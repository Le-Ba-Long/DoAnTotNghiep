package com.longkubi.qlns.model.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.longkubi.qlns.common.Constant;
import com.longkubi.qlns.model.entity.Certificate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CertificateDto {
    private UUID id;
    @NotBlank(message = "Trường Code Không Được Để Trống")
    @Pattern(regexp = Constant.REGEX_CODE_CERTIFICATE, message = "Code Chưa Đúng Format " + Constant.REGEX_CODE_CERTIFICATE)
    private String code;
    @NotBlank(message = "Trường Name Không Được Để Trống")
    private String name;
    @NotBlank(message = "Trường Majors Không Được Để Trống")
    private String majors;
    private Date issuedDate;
    private String grantedBy;
    private String creator;
    private Date dateCreated;
    private String changedBy;
    private Date dateChange;

    // private Set<Employee> employees;
    public static CertificateDto convertFromEntityToDto(Certificate entity) {
        return new ModelMapper().map(entity, CertificateDto.class);
    }
}
