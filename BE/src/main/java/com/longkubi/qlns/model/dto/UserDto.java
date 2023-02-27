package com.longkubi.qlns.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.longkubi.qlns.common.Constant;
import com.longkubi.qlns.model.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserDto {
    private UUID id;
    @NotBlank(message = "Tên Tài Khoản Không Được Để Trồng")
    private String userName;
    // @Length(min = 6, max = 12, message = "Mật Khẩu Không Được  ít hơn 6  và lớn hơn 12 kí tự")
    //@JsonIgnore
    private String passWord;
    //@Length(min = 6, max = 12, message = "Mật Khẩu Không Được  ít hơn 6  và lớn hơn 12 kí tự")
    private String newPassword;
    @NotBlank(message = "Họ Tên Không Được Để Trống")
    private String fullName;
    @NotBlank(message = "Email Không Được Để Trống")
    @Email(message = "Email Không Hợp Lệ")
    private String email;

    private String sex;

    private Date dateOfBirth;

    @Pattern(message = "Số Điện Thoại Không Hợp Lệ ", regexp = Constant.REGEX_PHONE)
    private String phone;
    private String address;
    @Lob
    private String avatar;
    //    private Set<RoleName> roleNames = new HashSet<>();
    private Set<RoleDto> roles = new HashSet<>();
    private Set<String> role = new HashSet<>();

    public UserDto(String userName, String fullName, String email) {
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
    }

    public UserDto() {
    }

    private static ModelMapper modelMapper = new ModelMapper();

    public UserDto(UUID id, String userName, String passWord, String fullName, String email, Set<RoleDto> roles) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.fullName = fullName;
        this.email = email;
        this.roles = roles;
    }

    public static UserDto convertDto(User user) {
        UserDto dto = modelMapper.map(user, UserDto.class);
        //   dto.setRoleNames(user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet()));
        dto.setRoles(user.getRoles().stream().map(role -> modelMapper.map(role, RoleDto.class)).collect(Collectors.toSet()));
        return dto;
    }
}
