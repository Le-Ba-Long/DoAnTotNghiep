package com.longkubi.qlns.model.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.longkubi.qlns.common.Constant;
import com.longkubi.qlns.model.entity.Employee;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class EmployeeDto {
    private UUID id;

    @NotBlank(message = "Trường Mã Nhân Viên Không Được Để Trống")
    @Pattern(regexp = Constant.REGEX_CODE_EMPLOYEE, message = "Mã Nhân Viên Chưa Đúng Format " + Constant.REGEX_CODE_EMPLOYEE)
    private String code;

    @NotBlank(message = "Trường Họ Tên Không Được Để Trống")
    private String fullName;//họ tên

    private String image;//ảnh

    private String imageName;//Tên ảnh thẻ

    private Date dateOfBirth;//Ngày Sinh

    private String sex;//Giới Tính

    @NotBlank(message = "Số Căn Cước Công Dân  Không Được Để Trống")
    private String numberIdentityCard;// Số Chứng Minh Nhân Dân

    private Date issuedDateIdentityCard;//ngày cấp căn cước công dân

    @NotBlank(message = "Nơi Cấp Căn Cước Công Dân  Không Được Để Trống")
    private String placeOfGrantIdentityCard;//nơi cấp Chứng minh nhân dân

    @NotBlank(message = "Địa Chỉ  Không Được Để Trống")
    private String address;// Địa chỉ

    @NotBlank(message = "Số Điện Thoại Không Được Để Trống")
    @Pattern(regexp = Constant.REGEX_PHONE, message = "Số Điện Thoại Chưa Đúng Format " + Constant.REGEX_PHONE)
    private String phone;// số điện thoại

    @NotBlank(message = "Dân Tộc Không Được Để Trống")
    private String nation;//dân tộc

    @NotBlank(message = "Tôn Giáo Không Được Để Trống")
    private String religion;//tôn giáo

    private String numberMedicalInsurance;//Số Bảo Hiểm Y Tế

    private String placeOfIssueMedicalInsurance;//Nơi cấp Bảo Hiểm Y Tế

    private Date issuedDateMedicalInsurance;//Ngày Phát Hành Bảo Hiểm Y Tế

    private String numberSocialInsurance;//Số Bảo Hiểm Xã Hội

    private String placeOfIssueSocialInsurance;//Nơi Cấp Bảo Hiểm Xã Hội

    private Date issuedDateSocialInsurance;//Ngày Cấp Bảo Hiểm Xã Hội

    private Date quitJobDate;//Ngày Nghỉ Việc

    private String education;//Trình Độ Học Vấn

    private String email;

    private String major;//chuyên Ngành

    private String refusalReason;// lý do từ chối hoặc là lí do nghỉ việc

    private String additionalRequestContent; // nội dung yêu cầu bổ sung

    private Byte status;

    private String creator;

    private Date dateCreated;

    private String changedBy;

    private Date dateChange;

    @ApiModelProperty(hidden = true)
    private Set<LanguageDto> languages;// Ngoại Ngữ

    private Set<CommendationAndDisciplineDto> commendations;// ds khen thưởng kỉ luật

      //@JsonBackReference
    private ContractDto contract;//Hợp Đồng

    private DepartmentDto department;//Phòng Ban Làm Việc Của Nhân Viên

//    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    Set<Leave> leaves;

    //    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    Set<PaymentSalary> paymentSalaries;
    @ApiModelProperty(hidden = true)
    private Set<PositionDto> positions;//Chức Vụ
    //
//    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<TimeKeeping> timeKeepings;
    private CertificateDto certificate;// Văn Bằng
    private CandidateProfileDto candidateProfileDto;
    //
//    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Salary> salaries;
    private static ModelMapper modelMapper = new ModelMapper();

    public static EmployeeDto convertFromEntityToDto(Employee entity) {
        EmployeeDto dto = modelMapper.map(entity, EmployeeDto.class);
        dto.setDepartment(modelMapper.map(entity.getDepartment(), DepartmentDto.class));
        dto.setPositions(entity.getPositions().stream().map(PositionDto::convertFromEntityToDto).collect(Collectors.toSet()));
        // dto.setCertificate(modelMapper.map(entity.getCertificate(), CertificateDto.class));
        return dto;
    }
}
