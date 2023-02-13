package com.longkubi.qlns.model.dto.request;

import com.longkubi.qlns.model.dto.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignUpForm {
    private String fullName;
    private String userName;
    private String email;
    private String passWord;
    private Set<RoleDto> roles = new HashSet<>();
}