package com.longkubi.qlns.model.dto.search;

import com.longkubi.qlns.common.Constant;
import com.longkubi.qlns.model.dto.CandidateProfileDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecruitSearchDto extends SearchDto {
    @Pattern(regexp = Constant.REGEX_CODE_RECRUIT, message = "Code Chưa Đúng Format " + Constant.REGEX_CODE_RECRUIT)
    private String code;
    private Set<CandidateProfileDto> candidateProfiles;
    private String description;
    private Byte status;

}
