package com.longkubi.qlns.service.impl;

import com.longkubi.qlns.common.ErrorMessage;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.UserDto;
import com.longkubi.qlns.model.entity.Role;
import com.longkubi.qlns.model.entity.RoleName;
import com.longkubi.qlns.model.entity.User;
import com.longkubi.qlns.repository.UserRepository;
import com.longkubi.qlns.service.IRoleService;
import com.longkubi.qlns.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static com.longkubi.qlns.common.ErrorMessage.*;

@Transactional
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //    @Override
//    public ResponseData<List<UserDto>> getAll() {
//        return new ResponseData<>(ErrorMessage.SUCCESS, userRepository.findAll().stream().map(user -> {
//            return modelMapper.map(user, UserDto.class);
//        }).collect(Collectors.toList()));
//    } @Override
    public ResponseData<List<UserDto>> getAll() {
        return new ResponseData<>(ErrorMessage.SUCCESS, userRepository.findAll().stream().map(UserDto::convertDto).collect(Collectors.toList()));

    }

    @Override
    public ResponseData<?> insert(UserDto userDto) {
        return new ResponseData<>(ErrorMessage.SUCCESS, modelMapper.map(userRepository.save(modelMapper.map(userDto, User.class)), UserDto.class));
    }

    @Override
    public ResponseData<?> update(UUID id, UserDto userDto) {
//        if (userRepository.existsByUserName(userDto.getUserName()))
//            return new ResponseData<>(ACCOUNT_NAME_ALREADY_EXISTS, null);
        Optional<User> user = userRepository.findById(id);
//        if (userService.existsByEmail(userDto.getEmail())) {
//            return new ResponseData<>(THE_EMAIL_IS_EXISTED, null);
//        }
        if (user.isPresent()) {
            User entity = user.get();
            entity.setUserName(userDto.getUserName());
            if (StringUtils.hasText(userDto.getNewPassword())) {
                entity.setPassWord(passwordEncoder.encode(userDto.getNewPassword()));
            }
            entity.setFullName(userDto.getFullName());
            entity.setEmail(userDto.getEmail());
            entity.setRoles(userDto.getRoles().stream().map(roleDto -> modelMapper.map(roleDto, Role.class)).collect(Collectors.toSet()));
            entity.setAvatar(userDto.getAvatar());
            entity.setAddress(userDto.getAddress());
            entity.setSex(userDto.getSex());
            entity.setDateOfBirth(userDto.getDateOfBirth());
            entity.setPhone(userDto.getPhone());
            return new ResponseData<>(SUCCESS, modelMapper.map(userRepository.save(entity), UserDto.class));
        }
        return new ResponseData<>(UPDATE_FAILED, null);


    }

    @Override
    public ResponseData<Boolean> deleteById(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return new ResponseData<>(SUCCESS, true);
        }
        return new ResponseData<>(ERROR, false);
    }

    @Override
    public ResponseData<Boolean> checkUserNameAndPassWord(String userName, String passWord) {
        return new ResponseData<>(SUCCESS, userRepository.existsByUserNameAndAndPassWord(userName, passWord));
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public ResponseData<Boolean> changPassword(UserDto dto) {
        if (!userService.existsByUserName(dto.getUserName()))
            return new ResponseData<>(ACCOUNT_NAME_NOT_EXISTS, false);
        Optional<UserDto> userDto = userService.loadUserByUsername(dto.getUserName());
        if (!passwordEncoder.matches(dto.getPassWord(), userDto.get().getPassWord()))
            return new ResponseData<>(OLD_PASSWORD_INCORRECT, false);
        User user = userRepository.getUserById(dto.getId());
        user.setPassWord(passwordEncoder.encode(dto.getNewPassword()));
        return new ResponseData<>(SUCCESS, true);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    @Override
    public Optional<UserDto> loadUserByUsername(String userName) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepository.findByUserName(userName).map(user -> new ModelMapper().map(user, UserDto.class))
                .orElseThrow(() -> new UsernameNotFoundException("User is not found -> user name or password" + userName)));

    }
}
