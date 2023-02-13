package com.longkubi.qlns.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_salary")//Bảng Lương
public class Salary implements Serializable {
    @Transient
    private static final long serialVersionUID = 4559994432567537044L;
    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    @Column(name = "code")
    private String code;

    @Column(name = "hourly_rate")
    private long hourlyRate;//Số tiền tính cho 1h làm thêm

    @Column(name = "salary_level")
    private long salaryLevel;//Mức Lương Cơ Bản

    @Column(name = "coefficient_salary")
    private double coefficientSalary; //Hệ Số Lương

//    @OneToOne(mappedBy = "salary")
//    @JsonBackReference
//    PaymentSalary paymentSalary;//mã Thanh Toán Lương
    //    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
//    @JoinColumn(name = "employee_code")
//    private Employee employee;
    @Column(name = "creator")
    String creator;

    @Column(name = "date_created")
    Date dateCreated;

    @Column(name = "changed_by")
    String changedBy;

    @Column(name = "date_change")
    Date dateChange;
}
