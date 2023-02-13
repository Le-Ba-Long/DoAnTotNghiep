package com.longkubi.qlns.model.dto.search;

import com.longkubi.qlns.common.Constant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalarySearchDto extends SearchDto {

    @Pattern(regexp = Constant.REGEX_CODE_LANGUAGE, message = "Mã Lương Không Đúng Format " + Constant.REGEX_CODE_LANGUAGE)
    private String code;

    private long hourlyRate;//Số tiền tính cho 1h làm thêm

    private long salaryLevel;//Mức Lương Cơ Bản

    private double coefficientSalary; //Hệ Số Lương
}