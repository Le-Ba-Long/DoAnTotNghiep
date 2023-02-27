package com.longkubi.qlns.model.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.models.auth.In;
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
    private Byte month;
    private Short year;
    private Integer advancePayment;//Tạm Ứng

    //    private Long bonus;//Thưởng
//
//    private Long punish;//Phạt
    private Integer fullTimeSalary;//tiền lương chuyên cần đc cộng thêm khi đi làm đầy đủ
    private Integer transportationAndLunchAllowance;//tiền trợ cấp đi lại và ăn trưa
    private Float socialInsuranceCosts;//Chi phí đóng bảo hiểm xã hội (8% tổng thu nhập)


    private Float healthInsurancePremium;//Chi phí đóng bảo hiểm  y tế (1.5% tổng thu nhập)


    private Float personalIncomeTax;//tiền thuế thu nhập cá nhân


    private Float valueAddedWithEachSalary;//giá trị gia tăng thuế thu nhập cá nhân theo từng mức lương

    private Byte status;//trạng thái

    private Float insuranceDeductible;//Khấu Trừ Bảo Hiểm

    private BigDecimal netWage;//Lương Thực Lĩnh

    //    private EmployeeDto employee;
    private TimeKeepingDto timeKeeping;//Mã Công

    // private CommendationAndDisciplineDto commendationAndDiscipline;//Mã Khen Thưởng Kỉ Luật

    // private SalaryDto salary;//Mã Lương
}
