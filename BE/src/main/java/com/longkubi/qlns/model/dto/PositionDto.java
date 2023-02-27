package com.longkubi.qlns.model.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.longkubi.qlns.common.Constant;
import com.longkubi.qlns.model.entity.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PositionDto {
    private UUID id;
    @NotBlank(message = "Trường Code Không Được Để Trống")
    @Pattern(regexp = Constant.REGEX_CODE_POSITION, message = "Code Chưa Đúng Format " + Constant.REGEX_CODE_POSITION)
    private String code;
    @NotBlank(message = "Trường Name Không Được Để Trống")
    @Size(min = 5, max = 100, message = "Tên chức vụ Không Được Nhỏ Hơn 5 Kí Tự Và Dài Hơn 100 Kí Tự ")
    private String name;
    private String description;
    private String creator;
    private Date dateCreated;
    private String changedBy;
    private Date dateChange;

    //private Set<Employee> employees;
    public static PositionDto convertFromEntityToDto(Position entity) {
        return new ModelMapper().map(entity, PositionDto.class);
    }
}
