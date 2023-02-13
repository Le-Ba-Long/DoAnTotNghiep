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
public class LanguageSearchDto extends SearchDto {

    @Pattern(regexp = Constant.REGEX_CODE_LANGUAGE, message = "Mã Ngoại Ngữ Không Đúng Format " + Constant.REGEX_CODE_LANGUAGE)
    private String code;

    @Size(min = 5, max = 50, message = "Tên Ngoại Ngữ Không được dài quá 50 kí tự và nhỏ hơn 5 kí tự ")
    private String name;


}
