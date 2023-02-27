package com.longkubi.qlns.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_time_keeping")//Bảng Chấm Công
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TimeKeeping implements Serializable {
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
    @Column(name = "number_work_day")
    private byte numberWorkDay;//Số Ngày Công Đi Làm

    //    @Column(name = "_leave")
    //    private int leave;
    @Column(name = "number_day_off")
    private byte numberDayOff;//Số Ngày Nghỉ Có Phép

    @Column(name = "number_day_unexcused_leave")
    private byte numberDayUnexcusedLeave;//Số Ngày Nghỉ Không Phép

    @Column(name = "_month")
    private byte month;

    @Column(name = "_year")
    private short year;

    @Column(name = "number_overtime_hours")
    private short numberOvertimeHours; //Giờ Làm Thêm

    @Column(name = "status")
    private byte status;

    @Column(name = "creator")
    String creator;

    @Column(name = "date_created")
    Date dateCreated;

    @Column(name = "changed_by")
    String changedBy;

    @Column(name = "date_change")
    Date dateChange;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_code", nullable = false)
    private Employee employee;//Mã Nhân Viên
    //
    //    @OneToOne(mappedBy = "timeKeeping")
    //    @JsonBackReference
    //    private CommendationAndDiscipline commendationAndDiscipline;// Mã Khen Thưởng Kỉ Luật
    //
    // @OneToOne(mappedBy = "timeKeeping",cascade = CascadeType.ALL,orphanRemoval = true)
    // @JsonBackReference
    @OneToOne(mappedBy = "timeKeeping", cascade = CascadeType.ALL, orphanRemoval = true)
    private PaymentSalary paymentSalary;//Mã Thanh Toán Lương

}
