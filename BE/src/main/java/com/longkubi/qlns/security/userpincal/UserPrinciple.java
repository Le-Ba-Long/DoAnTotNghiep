package com.longkubi.qlns.security.userpincal;

import com.longkubi.qlns.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
public class UserPrinciple implements UserDetails {
    private UUID id;
    @NotBlank(message = "Tên Tài Khoản Không Được Để Trồng")
    private String userName;
    @Length(min = 6, max = 12, message = "Mật Khẩu Không Được  ít hơn 6  và lớn hơn 12 kí tự")
    private String passWord;
    @NotBlank(message = "Họ Tên Không Được Để Trống")
    private String fullName;
    @NotBlank(message = "Email Không Được Để Trống")
    @Email(message = "Email Không Hợp Lệ")
    private String email;
    private String avatar;
    private Collection<? extends GrantedAuthority> roles;

    public static UserPrinciple build(User users) {
        List<GrantedAuthority> authorities = users.getRoles()
                .stream().map(role -> new SimpleGrantedAuthority
                        (role.getRoleName().name())).collect(Collectors.toList());
        return new UserPrinciple(users.getId(), users.getFullName(),
                users.getUserName(), users.getEmail(),
                users.getPassWord(), users.getAvatar(),
                authorities);
    }
    public UserPrinciple(UUID id, String name, String username, String email, String password, String avatar, Collection<? extends GrantedAuthority> roles) {
        this.id = id;
        this.fullName = name;
        this.userName = username;
        this.email = email;
        this.passWord = password;
        this.avatar = avatar;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return passWord;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
