package com.longkubi.qlns.rest;

import com.longkubi.qlns.common.ErrorMessage;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.UserDto;
import com.longkubi.qlns.model.dto.request.SignInForm;
import com.longkubi.qlns.model.dto.request.SignUpForm;
import com.longkubi.qlns.model.dto.response.JwtResponse;
import com.longkubi.qlns.model.dto.response.ResponseMessage;
import com.longkubi.qlns.model.entity.Role;
import com.longkubi.qlns.model.entity.RoleName;
import com.longkubi.qlns.model.entity.User;
import com.longkubi.qlns.security.jwt.JwtProvider;
import com.longkubi.qlns.security.userpincal.UserDetailService;
import com.longkubi.qlns.security.userpincal.UserPrinciple;
import com.longkubi.qlns.service.IUserService;
import com.longkubi.qlns.service.impl.RoleServiceImpl;
import com.longkubi.qlns.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;
import java.util.stream.Collectors;

import static com.longkubi.qlns.common.ErrorMessage.SUCCESS;
import static com.longkubi.qlns.common.ErrorMessage.THE_EMAIL_IS_EXISTED;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/auth")
@RestController
@Slf4j
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final IUserService userService;

    private final RoleServiceImpl roleService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;
    private final ModelMapper modelMapper;
    @Autowired
    private UserDetailService userDetailService;

    public AuthController(RoleServiceImpl roleService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtProvider jwtProvider, UserServiceImpl userService, ModelMapper modelMapper) {
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
        this.modelMapper = modelMapper;

    }

    @PostMapping("/signup")
    public ResponseData<?> register(@Valid @RequestBody UserDto userDto) {
        if (userService.existsByUserName(userDto.getUserName())) {
            return new ResponseData<>(ErrorMessage.THE_USERNAME_IS_EXISTED, null);
        }
        if (userService.existsByEmail(userDto.getEmail())) {
            return new ResponseData<>(THE_EMAIL_IS_EXISTED, null);
        }
        User users = new User(userDto.getFullName(), userDto.getUserName(), userDto.getEmail(), passwordEncoder.encode(userDto.getPassWord()), userDto.getRoles().stream().map(roleDto -> modelMapper.map(roleDto, Role.class)).collect(Collectors.toSet()));
//        Set<String> strRoles = signUpForm.getRoles();
//        Set<Role> roles = new HashSet<>();
//        strRoles.forEach(role -> {
//            switch (role) {
//                case "ADMIN":
//                    Role roleAdmin = roleService.findByRoleName(RoleName.ADMIN).orElseThrow(this::runtimeException);
//                    roles.add(roleAdmin);
//                    break;
//                case "LEADER":
//                    Role roleLeader = roleService.findByRoleName(RoleName.LEADER).orElseThrow(this::runtimeException);
//                    roles.add(roleLeader);
//                    break;
//                case "RECRUITMENT":
//                    Role roleRecruitment = roleService.findByRoleName(RoleName.RECRUITMENT).orElseThrow(this::runtimeException);
//                    roles.add(roleRecruitment);
//                    break;
//                    case "ACCOUNTANCY":
//                    Role roleAccountancy = roleService.findByRoleName(RoleName.ACCOUNTANCY).orElseThrow(this::runtimeException);
//                    roles.add(roleAccountancy);
//                    break;
//                case "MGT User":
//                    Role mgtUserRole = roleService.findByRoleName(RoleName.MGT_User).orElseThrow(this::runtimeException);
//                    roles.add(mgtUserRole);
//                    break;
//                default:
//                    throw runtimeException();
////                default:
////                    Role userRole = roleService.findByRoleName(RoleName.USER).orElseThrow( ()-> new runtimeException("Role not found"));
////                    roles.add(userRole);
//            }
//        });
        userService.save(users);
        return new ResponseData<>(SUCCESS);
    }

    private RuntimeException runtimeException() {
        return new RuntimeException("Role not found");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody SignInForm signInForm) {

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtProvider.createToken(authentication);
            UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
            log.info(String.valueOf(passwordEncoder.matches(signInForm.getPassword(), userPrinciple.getPassword())));
            return ResponseEntity.ok(new JwtResponse(token, userPrinciple.getFullName(), 200, userPrinciple.getAuthorities()));
        } catch (AuthenticationException e) {
            Map<String, Object> errors = new HashMap<>();
            errors.put("code", 400);
            errors.put("error", "Tên Đăng Nhập Hoặc Mật Khẩu Không Chính Xác !");
            Optional<UserDto> userDto = userService.loadUserByUsername(signInForm.getUsername());
            log.info(String.valueOf(passwordEncoder.matches(signInForm.getPassword(), userDto.get().getPassWord())));
            return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/change-password")
    public ResponseData<Boolean> changePassword(@RequestBody UserDto userDto) {
        return userService.changPassword(userDto);
    }

    @PostMapping("/checkToken")
    public ResponseEntity<?> checkToken(@Valid @RequestBody SignInForm signInForm) {
        try {
            Optional<UserDto> userDto = userService.loadUserByUsername(signInForm.getUsername());
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword()));
            UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
            return ResponseEntity.ok().body(userPrinciple);
        } catch (Exception e) {
            log.error("Can't set user authentication -> Message: {0}", e);
            return ResponseEntity.badRequest().body("Can't set user authentication -> Message: {0}" + e);
        }

    }

    private String getJwt(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }
        return null;
    }
}