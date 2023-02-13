package com.longkubi.qlns.model.dto.search;

import com.longkubi.qlns.model.dto.TimeKeepingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentSalarySearchDto extends SearchDto {
    private Integer month;

    private Integer year;

    private Double advancePayment;//Tạm Ứng

//    private Long bonus;//Thưởng
//
//    private Long punish;//Phạt

    private Integer status;//trạng thái

    private Double insuranceDeductible;//Khấu Trừ Bảo Hiểm

    private Double netWage;//Lương Thực Lĩnh

    private UUID employeeId;//mã Nhân Viên
//    private EmployeeDto employee;

    private TimeKeepingDto timeKeeping;//Mã Công

}
