package com.longkubi.qlns.model.dto.search;

import com.longkubi.qlns.common.Constant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepartmentSearchDto extends SearchDto {
    @Pattern(regexp = Constant.REGEX_CODE_DEPARTMENT, message = "Mã Phòng Ban Không Đúng Format " + Constant.REGEX_CODE_DEPARTMENT)
    private String code;
    @Size(min = 5, max = 50, message = "Tên Phòng Ban Không được dài quá 50 kí tự và nhỏ hơn 5 kí tự")
    private String name;

}
