package com.longkubi.qlns.model.entity;

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
@Table(name = "tbl_commendation_and_discipline")//Khen Thưởng Kỉ Luật
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
    private int status;

    @Column(name = "type")
    private int type;// 1 là kỉ luật / 2 khen thưởng

    //    @Column(name = "number_money")
//    private Long numberMoney;//Số Tiền Thưởng Or Phạt
    @Column(name = "staff_name")
    private String staffName;

    @Column(name = "reason", columnDefinition = "nvarchar(255)")
    private String reason;//lí do khen thưởng kỉ luật

    @Column(name = "issued_date")
    private Date issuedDate;//ngày áp dụng

    @Column(name = "_day")
    private int day;
    @Column(name = "_month")
    private int month;

    @Column(name = "_year")
    private int year;

    @Column(name = "decision_number")
    private String decisionNumber;// số quyết định

    @Column(name = "decision_day")
    private Date decisionDay;// ngày quyết định

    @Column(name = "reward_discipline_level")
    private double rewardDisciplineLevel;// mức khen thưởng kỉ luật

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

//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "timeKeeping_id", referencedColumnName = "id")
//    @JsonManagedReference
//    private TimeKeeping timeKeeping;//Mã Chấm Công
}
