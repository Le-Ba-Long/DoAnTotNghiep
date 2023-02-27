package com.longkubi.qlns.model.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.longkubi.qlns.common.Constant;
import com.longkubi.qlns.model.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class DepartmentDto {
    private UUID id;
    @NotBlank(message = "Code Không Được Để Trống")
    @Pattern(regexp = Constant.REGEX_CODE_DEPARTMENT, message = "Code Chưa Đúng Format " + Constant.REGEX_CODE_DEPARTMENT)
    private String code;
    @NotBlank(message = "Name Không Được Để Trống")
    @Size(min = 5, max = 100, message = "Tên Phòng Ban Phải Lớn Hơn 5 Kí Tự Và Nhỏ Hơn 100 Kí Tự")
    private String name;
    private String description;
    private String creator;
    private Date dateCreated;
    private String changedBy;
    private Date dateChange;
    private Set<EmployeeDto> employeeDtos;
    public static DepartmentDto convertFromEntityToDto(Department entity) {
        return new ModelMapper().map(entity, DepartmentDto.class);
    }
}
