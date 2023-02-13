package com.longkubi.qlns.model.dto.search;

import com.longkubi.qlns.common.Constant;
import com.longkubi.qlns.model.entity.Recruit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CandidateProfileSearchDto extends SearchDto{

    @NotBlank(message = "Trường Code Không Được Để Trống")
    @Pattern(regexp = Constant.REGEX_CODE_CERTIFICATE, message = "Code Chưa Đúng Format " + Constant.REGEX_CODE_CERTIFICATE)
    private String code;

    private String fullName;

    private Date dateOfBirth;

    @NegativeOrZero
    private Integer age;

    private String address;

    @Email(message = "Email Không Hợp Lệ", regexp = Constant.REGEX_EMAIL)
    private String email;

    @Pattern(message = "Số Điện Thoại Không Hợp Lệ ", regexp = Constant.REGEX_PHONE)
    private String phone;

    private String education;

    private String major;

    private Recruit recruit;

    private String creator;

    private Date dateCreated;

    private String changedBy;

    private Date dateChange;

}
