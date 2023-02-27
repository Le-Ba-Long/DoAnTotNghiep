package com.longkubi.qlns.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
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
@Table(name = "tbl_commendation_and_discipline")//Khen Thưởng Kỉ Luật
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CommendationAndDiscipline implements Serializable {
    @Transient
    private static final long serialVersionUID = 4559994432567537044L;
    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    @Column(name = "status")
    private byte status;

    @Column(name = "type")
    private byte type;// 1 là kỉ luật / 2 khen thưởng / 3 tăng lương

    //    @Column(name = "number_money")
//    private Long numberMoney;//Số Tiền Thưởng Or Phạt
    @Column(name = "staff_name")
    private String staffName;

    @Column(name = "reason", columnDefinition = "nvarchar(255)")
    private String reason;//lí do khen thưởng kỉ luật

    @Column(name = "issued_date")
    private Date issuedDate;//ngày áp dụng

    @Column(name = "_day")
    private byte day;

    @Column(name = "_month")
    private byte month;

    @Column(name = "_year")
    private short year;

    @Column(name = "decision_number")
    private String decisionNumber;// số quyết định

    @Column(name = "decision_day")
    private Date decisionDay;// ngày quyết định

    @Column(name = "reward_discipline_level")
    private float rewardDisciplineLevel;// mức khen thưởng kỉ luật

    @Column(name = "basic_salary")
    private int basicSalary;// mức lương cơ bản

    @Column(name = "coefficient_salary")
    private float coefficientSalary; //Hệ Số Lương

    @Column(name = "hourly_rate")
    private int hourlyRate;//Số tiền tính cho 1h làm thêm

    @Column(name = "creator")
    private String creator;

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "changed_by")
    private String changedBy;

    @Column(name = "date_change")
    private Date dateChange;

    //@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_code", nullable = false)
    private Employee employee;

//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "timeKeeping_id", referencedColumnName = "id")
//    @JsonManagedReference
//    private TimeKeeping timeKeeping;//Mã Chấm Công
}
