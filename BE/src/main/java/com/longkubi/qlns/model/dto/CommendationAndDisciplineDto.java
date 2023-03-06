package com.longkubi.qlns.model.dto;

import com.longkubi.qlns.model.entity.CommendationAndDiscipline;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CommendationAndDisciplineDto {
    private UUID id;

    //    @Column(name = "number_money")
//    private Long numberMoney;//Số Tiền Thưởng Or Phạt
    private Byte type;// 1 là kỉ luật / 2 khen thưởng / 3 tăng lương

    private String reason;//lí do khen thưởng kỉ luật

    private Date issuedDate;//ngày áp dụng

    private Byte day;// ngày

    private Byte month;// tháng

    private String staffName;// tên nhân viên

    private Short year;// năm

    private String decisionNumber;// số quyết định

    private Date decisionDay;// ngày quyết định

    private Float rewardDisciplineLevel;// mức khen thưởng kỉ luật (tiền)

    private Integer basicSalary;// mức lương cơ bản

    private Float coefficientSalary; //Hệ Số Lương

    private Integer hourlyRate;//Số tiền tính cho 1h làm thêm

    private Byte status;

    private String creator;

    private Date dateCreated;

    private String changedBy;

    private Date dateChange;

    private EmployeeDto employeeDto;
    private static ModelMapper modelMapper = new ModelMapper();

    public static CommendationAndDisciplineDto convertFromEntityToDto(CommendationAndDiscipline entity) {
        CommendationAndDisciplineDto dto = modelMapper.map(entity, CommendationAndDisciplineDto.class);
        if (!Objects.isNull(entity.getEmployee())) {
            dto.setEmployeeDto(modelMapper.map(entity.getEmployee(), EmployeeDto.class));
        //    dto.setEmployeeDto(EmployeeDto.convertFromEntityToDto(entity.getEmployee()));
        }

        return dto;
    }

}
