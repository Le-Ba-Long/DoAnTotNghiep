package com.longkubi.qlns.model.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.longkubi.qlns.model.entity.CommendationAndDiscipline;
import com.longkubi.qlns.model.entity.Employee;
import com.longkubi.qlns.model.entity.PaymentSalary;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
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

    private Integer numberWorkDay;//Số Ngày Công Đi Làm

    private Integer status;//


    //    @Column(name = "_leave")
    //    private int leave;

    private Integer numberDayOff;//Số Ngày Nghỉ Có Phép

    private Integer numberDayUnexcusedLeave;//Số Ngày Nghỉ Không Phép

    private Integer month;//tháng

    private Integer year;//năm

    private Integer numberOvertimeHours; //Giờ Làm Thêm

    private String creator;

    private Date dateCreated;


    private String changedBy;


    private Date dateChange;

    //private CommendationAndDisciplineDto commendationAndDiscipline;

    private PaymentSalaryDto paymentSalary;
}
