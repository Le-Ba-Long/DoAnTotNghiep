package com.longkubi.qlns.model.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PaymentSalaryDto {

    private UUID id;

    //private String code;

    private Integer month;

    private Integer year;

    private Double advancePayment;//Tạm Ứng

    //    private Long bonus;//Thưởng
//
//    private Long punish;//Phạt


    private double fullTimeSalary;//tiền lương chuyên cần đc cộng thêm khi đi làm đầy đủ


    private double transportationAndLunchAllowance;//tiền trợ cấp đi lại và ăn trưa


    private double socialInsuranceCosts;//Chi phí đóng bảo hiểm xã hội (8% tổng thu nhập)


    private double healthInsurancePremium;//Chi phí đóng bảo hiểm  y tế (1.5% tổng thu nhập)


    private double personalIncomeTax;//tiền thuế thu nhập cá nhân


    private double valueAddedWithEachSalary;//giá trị gia tăng thuế thu nhập cá nhân theo từng mức lương

    private Integer status;//trạng thái

    private Double insuranceDeductible;//Khấu Trừ Bảo Hiểm

    private BigDecimal netWage;//Lương Thực Lĩnh

//    private EmployeeDto employee;

    private TimeKeepingDto timeKeeping;//Mã Công


    // private CommendationAndDisciplineDto commendationAndDiscipline;//Mã Khen Thưởng Kỉ Luật

    // private SalaryDto salary;//Mã Lương
}
