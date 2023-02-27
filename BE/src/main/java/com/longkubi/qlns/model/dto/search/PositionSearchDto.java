package com.longkubi.qlns.model.dto.search;

import com.longkubi.qlns.common.Constant;
import com.longkubi.qlns.model.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PositionSearchDto extends SearchDto {
    @Pattern(regexp = Constant.REGEX_CODE_POSITION, message = "Code Chưa Đúng Format " + Constant.REGEX_CODE_POSITION)
    private String code;
    @Size(min = 5, max = 50, message = "Tên Không Được Nhỏ Hơn 5 Kí Tự Và Lớn Hơn 100 Kí Tự")
    private String name;
    private Set<Employee> employees;
}
