package com.longkubi.qlns.service;

import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.UserDto;
import com.longkubi.qlns.model.entity.User;
import com.longkubi.qlns.security.userpincal.UserPrinciple;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    ResponseData<List<UserDto>> getAll();

    ResponseData<?> insert(UserDto userDto);

    ResponseData<?> update(UUID id, UserDto userDto);

    ResponseData<?> deleteById(UUID id);

    ResponseData<?> checkUserNameAndPassWord(String userName, String passWord);

    Optional<User> findByUserName(String userName);

    User save(User user);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);
    Optional<UserDto> loadUserByUsername(String userName) throws UsernameNotFoundException ;

}
