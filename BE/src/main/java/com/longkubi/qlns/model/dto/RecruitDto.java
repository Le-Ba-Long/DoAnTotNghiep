package com.longkubi.qlns.model.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.longkubi.qlns.common.Constant;
import com.longkubi.qlns.model.entity.CandidateProfile;
import com.longkubi.qlns.model.entity.Certificate;
import com.longkubi.qlns.model.entity.Recruit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class RecruitDto {

    private UUID id;

    @NotBlank(message = "Trường Code Không Được Để Trống")
    @Pattern(regexp = Constant.REGEX_CODE_RECRUIT, message = "Code Chưa Đúng Format " + Constant.REGEX_CODE_RECRUIT)
    private String code;

    private String titleRecruit;//Tiêu Đề Job Tuyển Dụng

    private Set<CandidateProfileDto> candidateProfiles = new HashSet<>();


    private String description;//Mô tả(phòng ban vị trí tuyển dụng)


    private String requireRecruit;//Yêu Cầu Tuyển Dụng


    private String benefitsReceived;//Quyền Lợi Nhận Được


    private int quantity;//số Lượng Tuyển Dụng

    private String recruitmentChannel;//Kênh Tuyển Dụng

    private int status;//trạng thái

    private String feedback;//yêu cầu bổ sung

    private String creator;

    private Date dateCreated;

    private String changedBy;

    private Date dateChange;

    public static RecruitDto convertFromEntityToDto(Recruit entity) {
        RecruitDto dto = new ModelMapper().map(entity, RecruitDto.class);
        //  dto.setCandidateProfiles(entity.getCandidateProfiles().stream().map(CandidateProfileDto::convertFromEntityToDto).collect(Collectors.toSet()));
        return dto;
    }
}
