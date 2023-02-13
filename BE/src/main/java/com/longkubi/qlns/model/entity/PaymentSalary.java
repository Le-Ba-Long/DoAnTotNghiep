package com.longkubi.qlns.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_payment_salary")//Bảng Thanh Toán Lương
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PaymentSalary implements Serializable {
    @Transient
    private static final long serialVersionUID = 4559994432567537044L;
    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;

//    @Column(name = "code")
//    private String code;

    @Column(name = "_month")
    private int month;

    @Column(name = "_year")
    private int year;

    @Column(name = "advance_payment")
    double advancePayment;//Tạm Ứng

    @Column(name = "full_time_salary")
    private double fullTimeSalary;//tiền lương chuyên cần đc cộng thêm khi đi làm đầy đủ

    @Column(name = "transportation_and_lunch_allowance")
    private double transportationAndLunchAllowance;//tiền trợ cấp xăng xe và ăn trưa.

    @Column(name = "social_insurance_costs")
    private double socialInsuranceCosts;//Chi phí đóng bảo hiểm xã hội (8% tổng thu nhập)

    @Column(name = "health_insurance_premium")
    private double healthInsurancePremium;//Chi phí đóng bảo hiểm  y tế (1.5% tổng thu nhập)

    @Column(name = "personal_income_tax")
    private double personalIncomeTax;//tiền thuế thu nhập cá nhân

    @Column(name = "value_added_with_each_salary")
    private double valueAddedWithEachSalary;//giá trị gia tăng thuế thu nhập cá nhân theo từng mức lương


//    @Column(name = "bonus")
//    private long bonus;//Thưởng
//
//    @Column(name = "punish")
//    private long punish;//Phạt

    @Column(name = "status")
    private int status;

    @Column(name = "insurance_deductible")
    private double insuranceDeductible;//Khấu Trừ Bảo Hiểm

    @Column(name = "net_wage")
    private BigDecimal netWage;//Lương Thực Lĩnh
    @Column(name = "creator")
    private String creator;

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "changed_by")
    private String changedBy;

    @Column(name = "date_change")
    private Date dateChange;

//    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
//    @JoinColumn(name = "employee_code")
//    private Employee employee;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REMOVE})
    //@JoinColumn(name = "time_keeping_code", referencedColumnName = "code")
    @JoinColumn(name = "time_keeping_code", referencedColumnName = "id")
    // @JsonManagedReference
    TimeKeeping timeKeeping;//Mã Công

//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "commendation_and_discipline_code")
//    private CommendationAndDiscipline commendationAndDiscipline;//Mã Khen Thưởng Kỉ Luật

//    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST}, fetch = FetchType.LAZY)
//    @JoinColumn(name = "salary_code", referencedColumnName = "code")
//    @JsonManagedReference
//    private Salary salary;//Mã Lương
}
