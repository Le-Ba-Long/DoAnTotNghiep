package com.longkubi.qlns.model.dto.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeSearchDto extends SearchDto {


    private String code;

    private String fullName;

    private String image;

    private Date dateOfBirth;

    private String sex;

    private Integer numberIdentityCard;

    private String placeOfGrantIdentityCard;

    private String address;

    private String phone;

    private String nation;

    private String religion;

    private Integer numberMedicalInsurance;

    private String placeOfIssueMedicalInsurance;

    private Date issuedDateMedicalInsurance;

    private Integer numberSocialInsurance;

    private String placeOfIssueSocialInsurance;

    private Date issuedDateSocialInsurance;

    private Date quitJobDate;

    private Integer status;
}
