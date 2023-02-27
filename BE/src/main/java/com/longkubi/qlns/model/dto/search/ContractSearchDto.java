package com.longkubi.qlns.model.dto.search;

import com.longkubi.qlns.model.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContractSearchDto extends SearchDto {
    private String code;
    private Employee employee;
    private Date signingDate;
    private Date contractEffect;
    private String status;

}
