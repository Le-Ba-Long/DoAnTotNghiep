package com.longkubi.qlns.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_employee_history")//Nhân Viên
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmployeeHistory {
    @Transient
    private static final long serialVersionUID = 4559994432567537044L;
    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    //@Column(name = "working_position")
   // private String workingPosition;// vị trí(chức vụ) làm việc

    // @Column(name = "from_date")
    // private Date fromDate;// giữ chức vụ từ ngày

    //@Column(name = "to_date")
    //private Date toDate;// giữ chức vụ đến ngày

    // @Column(name = "start_date")
    // private Date startDate;// ngày vào cty

    @Column(name = "end_date")
    private Date endDate;// ngày nghỉ việc

    //@Column(name = "reward_or_discipline_day")
    //private Date rewardOrDisciplineDay;//ngày khen thưởng hoặc kỉ luật
   // @Column(name = "title_recruit")
   // private String titleRecruit;
    @Column(name = "description")
    @Lob
    private String description;// Mô Tả Chi Tiết Sự Kiện Xảy Ra

   // @Column(name = "working_department")
   // private String workingDepartment;// phòng ban làm việc

//    @Column(name = "basic_salary")
//    private int basicSalary;// mức lương cơ bản
//
//    @Column(name = "coefficient_salary")
//    private float coefficientSalary; //Hệ Số Lương
//
//    @Column(name = "hourly_rate")
//    private int hourlyRate;//Số tiền tính cho 1h làm thêm

    @Column(name = "event")
    private String event;// Tên sự kiện

    @Column(name = "_date")
    private Date date;// ngày xảy ra sự kiện

    private byte status;// trạng thái

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_code", referencedColumnName = "id", nullable = true)
    private Employee employeeHistory;
}
