package com.longkubi.qlns.model.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.longkubi.qlns.common.Constant;
import com.longkubi.qlns.model.entity.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class LanguageDto {

    private UUID id;

    @NotBlank(message = "Không Được Để Trống  Trường Code")
    @Pattern(message = "Mã Ngoại Ngữ Không Đúng Định Dạng "+Constant.REGEX_CODE_LANGUAGE, regexp = Constant.REGEX_CODE_LANGUAGE)
    private String code;

    @NotBlank(message = "Không Được Để Trống Trường Name")
    @Size(min = 5, max = 100, message = "Tên Ngoại Ngữ Không Được Nhỏ Hơn 5 Kí Tự Và Dài Hơn 100 Kí Tự ")

    private String name;

    private String description;

    private String creator;

    private Date dateCreated;

    private String changedBy;

    private Date dateChange;


    public static LanguageDto convertFromEntityToDto(Language entity) {
        return new ModelMapper().map(entity, LanguageDto.class);
    }
}
