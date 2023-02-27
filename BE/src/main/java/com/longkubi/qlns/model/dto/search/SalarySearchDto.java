package com.longkubi.qlns.model.dto.search;

import com.longkubi.qlns.common.Constant;
import io.swagger.models.auth.In;
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
    private Integer hourlyRate;//Số tiền tính cho 1h làm thêm
    private Integer salaryLevel;//Mức Lương Cơ Bản
    private Float coefficientSalary; //Hệ Số Lương
}