package com.longkubi.qlns.model.dto;

import com.longkubi.qlns.common.Constant;
import com.longkubi.qlns.model.entity.CandidateProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CandidateProfileDto {

    private UUID id;
    @NotBlank(message = "Trường Code Không Được Để Trống")
    @Pattern(regexp = Constant.REGEX_CODE_CANDIDATE_PROFILE, message = "Code Chưa Đúng Format " + Constant.REGEX_CODE_CANDIDATE_PROFILE)
    private String code;
    @NotBlank(message = "Tên Không Được Để Trống")
    private String fullName;
    private Date dateOfBirth;
    @Positive
    private Byte age;
    @NotBlank
    private String address;
    @Email(message = "Email Không Hợp Lệ", regexp = Constant.REGEX_EMAIL)
    @NotEmpty(message = "Email Không Được Để Trống")
    private String email;
    @Pattern(message = "Số Điện Thoại Không Hợp Lệ ", regexp = Constant.REGEX_PHONE)
    private String phone;
    private String education;//Trình Độ Học Vấn
    private String major;//Chuyên Ngành
    private String interviewer;// người hẹn phỏng  vấn
    private Date interviewDate;//ngày hẹn phỏng vấn
    private String image;//ảnh thẻ
    private String imageName;//Tên ảnh thẻ
    private String refusalReason;//lý do  từ chối
    private String workingExperience;//Kinh nghiệm làm việc
    private String hobby;//sở thích
    private String skill;// các kĩ năng biết
    private String careerGoals;// mục tiêu nghề nghiệp
    private Byte status;//trạng thái
    private List<RecruitDto> recruitDtos = new ArrayList<>();//Job Tuyển Dụng
    private String creator;
    private Date dateCreated;
    private String changedBy;
    private Date dateChange;
    private EmployeeDto employeeDto;
    private static ModelMapper modelMapper = new ModelMapper();
//    public static CandidateProfileDto convertFromEntityToDto(CandidateProfile entity) {
//        CandidateProfileDto dto = modelMapper.map(entity, CandidateProfileDto.class);
//        return dto;
//    }
    public static CandidateProfileDto convertFromEntityToDto(CandidateProfile entity) {
        CandidateProfileDto dto = modelMapper.map(entity, CandidateProfileDto.class);
        dto.setRecruitDtos(entity.getRecruit()
                .stream().map(RecruitDto::convertFromEntityToDto)
                .collect(Collectors.toList()));
        return dto;
    }


}
