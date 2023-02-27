package com.longkubi.qlns.model.dto.search;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.longkubi.qlns.model.dto.PaymentSalaryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TimeKeepingSearchDto extends SearchDto {
    private String code;//mã chấm công

    //private EmployeeDto employee;//nhân viên
    private UUID employeeId;
    private Byte numberWorkDay;//Số Ngày Công Đi Làm

    //    @Column(name = "_leave")
    //    private int leave;
    private Byte numberDayOff;//Số Ngày Nghỉ Có Phép
    private Byte numberDayUnexcusedLeave;//Số Ngày Nghỉ Không Phép
    private Byte month;//tháng
    private Short year;//năm
    private Short numberOvertimeHours; //Giờ Làm Thêm
    private PaymentSalaryDto paymentSalary;
    private Byte status;
}
