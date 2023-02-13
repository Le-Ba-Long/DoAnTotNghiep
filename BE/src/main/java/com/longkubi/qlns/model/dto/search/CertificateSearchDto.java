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
public class CertificateSearchDto extends SearchDto {

    private String code;

    private String name;

    private String majors;

    private Date issuedDate;

    private String grantedBy;
}
