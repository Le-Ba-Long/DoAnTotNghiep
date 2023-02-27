package com.longkubi.qlns.model.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TimeKeepingDto {

    private UUID id;
    private String code;//mã chấm công
    private EmployeeDto employee;//nhân viên
    private Byte numberWorkDay;//Số Ngày Công Đi Làm
    private Byte status;//

    //    @Column(name = "_leave")
    //    private int leave;
    private Byte numberDayOff;//Số Ngày Nghỉ Có Phép
    private Byte numberDayUnexcusedLeave;//Số Ngày Nghỉ Không Phép
    private Byte month;//tháng
    private Short year;//năm
    private Short numberOvertimeHours; //Giờ Làm Thêm
    private String creator;
    private Date dateCreated;
    private String changedBy;
    private Date dateChange;
    //private CommendationAndDisciplineDto commendationAndDiscipline;
    private PaymentSalaryDto paymentSalary;
}
