package com.longkubi.qlns.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

//@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_user")

public class User implements Serializable {
    @Transient
    private static final long serialVersionUID = 4559994432567537044L;
    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;
    @Column(name = "user_name")
    private String userName;

  // @JsonIgnore
    @Column(name = "pass_word")
    private String passWord;
    @Column(name = "full_name", columnDefinition = "nvarchar(255)")
    private String fullName;
    @Column(name = "email")
    private String email;

    private String phone;
    private String sex;

    private Date dateOfBirth;

    private String address;
    @Lob
    private String avatar;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tbl_user_role", joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

    public User(String fullName, String userName, String email, String encode) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.passWord = encode;
    }

    public User(UUID id, String fullName, String username, String email, String password, String avatar, Set<Role> roles) {
        this.id = id;
        this.fullName = fullName;
        this.userName = username;
        this.email = email;
        this.passWord = password;
        this.avatar = avatar;
        this.roles = roles;
    }

    public User(String fullName, String username, String email, String password, Set<Role> roles) {
        this.fullName = fullName;
        this.userName = username;
        this.email = email;
        this.passWord = password;
        this.roles = roles;
    }
}
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import org.hibernate.annotations.GenericGenerator;
//import org.hibernate.annotations.Type;
//
//import javax.persistence.*;
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Size;
//import java.util.HashSet;
//import java.util.Set;
//import java.util.UUID;
//
//@Entity
//@Table(name = "users", uniqueConstraints = {
//        @UniqueConstraint(columnNames = {
//                "username"
//        }),
//        @UniqueConstraint(columnNames = {
//                "email"
//        })
//})
//public class User {
//    @Id
//    @Type(type = "uuid-char")
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
//    @Column(name = "id")
//    private UUID id;
//    @NotBlank
//    @Size(min = 3, max = 50)
//    private String fullName;
//    @NotBlank
//    @Size(min = 3,max = 50)
//    private String userName;
//    @NotBlank
//    @Size(max = 50)
//    @Email
//    private String email;
//    @JsonIgnore
//    @NotBlank
//    @Size(min = 6,max = 100)
//    private String password;
//    @Lob
//    private String avatar;
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
//    Set<Role> roles = new HashSet<>();
//
//    public User() {
//    }
//
//    public User(UUID id, String name, String username, String email, String password, String avatar, Set<Role> roles) {
//        this.id = id;
//        this.fullName = name;
//        this.userName = username;
//        this.email = email;
//        this.password = password;
//        this.avatar = avatar;
//        this.roles = roles;
//    }
//
//    public User(   @NotBlank
//                    @Size(min = 3, max = 50) String name,
//                    @NotBlank
//                    @Size(min = 3,max = 50) String username,
//                    @Size(max = 50)
//                    @Email String email,
//                    @NotBlank
//                    @Size(min = 6,max = 100)String encode) {
//        this.fullName = name;
//        this.userName = username;
//        this.email = email;
//        this.password = encode;
//    }
//
//    public UUID getId() {
//        return id;
//    }
//
//    public void setId(UUID id) {
//        this.id = id;
//    }
//
//    public String getFullName() {
//        return fullName;
//    }
//
//    public void setFullName(String name) {
//        this.fullName= name;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String username) {
//        this.userName = username;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getAvatar() {
//        return avatar;
//    }
//
//    public void setAvatar(String avatar) {
//        this.avatar = avatar;
//    }
//
//    public Set<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<Role> roles) {
//        this.roles = roles;
//    }
//}