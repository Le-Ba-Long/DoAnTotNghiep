package com.longkubi.qlns.model.dto.search;

import com.longkubi.qlns.model.dto.TimeKeepingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentSalarySearchDto extends SearchDto {
    private Byte month;
    private Short year;
    private Float advancePayment;//Tạm Ứng

//    private Long bonus;//Thưởng
//
//    private Long punish;//Phạt
    private Byte status;//trạng thái
    private Float insuranceDeductible;//Khấu Trừ Bảo Hiểm
    private BigDecimal netWage;//Lương Thực Lĩnh
    private UUID employeeId;//mã Nhân Viên
//    private EmployeeDto employee;
    private TimeKeepingDto timeKeeping;//Mã Công

}
