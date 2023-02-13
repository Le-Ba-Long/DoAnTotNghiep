package com.longkubi.qlns.model.dto.response;


import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtResponse {
    private String accessToken;
    private String type = "Bearer";
    private String name;
    private Integer code;
    private Collection<? extends GrantedAuthority> roles;

    public JwtResponse() {
    }

    public JwtResponse(String accessToken, String type, String name, Collection<? extends GrantedAuthority> roles) {
        this.accessToken = accessToken;
        this.type = type;
        this.name = name;
        this.roles = roles;
    }

    public JwtResponse(String accessToken, String name,Integer code, Collection<? extends GrantedAuthority> authorities) {
        this.accessToken = accessToken;
        this.name = name;
        this.code = code;
        this.roles = authorities;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Collection<? extends GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(Collection<? extends GrantedAuthority> roles) {
        this.roles = roles;
    }
}