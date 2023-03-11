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
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_employee")//Nhân Viên
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employee implements Serializable {
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
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "image")
    private String image;//url ảnh thẻ
    @Column(name = "imageimageName")
    private String imageName;//Tên ảnh thẻ
    @Column(name = "date_of_birth")
    private Date dateOfBirth;//Ngày Sinh
    @Column(name = "sex")
    private String sex;//Giới Tính
    @Column(name = "number_id_card")
    private String numberIdentityCard;// Số Chứng Minh Nhân Dân
    @Column(name = "issuance_date_identity_card")
    private Date issuedDateIdentityCard;// ngày cấp chứng minh nhân dân
    @Column(name = "place_of_grant_identity_card")
    private String placeOfGrantIdentityCard;//nơi cấp Chứng minh nhân dân
    @Column(name = "address")
    private String address;// Địa chỉ
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "nation")
    private String nation;//dân tộc
    @Column(name = "religion")
    private String religion;//tôn giáo
    @Column(name = "number_medical_insurance")
    String numberMedicalInsurance;//Số Bảo Hiểm Y Tế
    @Column(name = "place_of_issue_medical_insurance")
    private String placeOfIssueMedicalInsurance;//Nơi cấp Bảo Hiểm Y Tế
    @Column(name = "issued_date_medical_insurance")
    private Date issuedDateMedicalInsurance;//Ngày Phát Hành Bảo Hiểm Y Tế
    @Column(name = "number_social_insurance")
    private String numberSocialInsurance;//Số Bảo Hiểm Xã Hội
    @Column(name = "place_of_issue_social_insurance")
    private String placeOfIssueSocialInsurance;//Nơi Cấp Bảo Hiểm Xã Hội
    @Column(name = "issued_date_social_insurance")
    private Date issuedDateSocialInsurance;//Ngày Cấp Bảo Hiểm Xã Hội
    @Column(name = "quit_job_date")
    private Date quitJobDate;//Ngày Nghỉ Việc
    @Column(name = "education")
    private String education;//Trình Độ Học Vấn
    @Column(name = "major")
    private String major;//chuyên Ngành
    @Column(name = "refusal_reason")
    private String refusalReason;// lý do từ chối(hoặc lý do nghỉ việc ,sa thải)
    @Column(name = "additional_request_content")
    private String additionalRequestContent; // nội dung yêu cầu bổ sung
    @Column(name = "note")
    private String note; // nghi chú thích thêm quá trình công tác ở đâu về....
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
    @Column(name = "title_recruit")
    private String titleRecruit;// ví trí làm việc
    @ManyToMany
    @JoinTable(name = "tbl_employee_language", joinColumns = @JoinColumn(name = "employee_code"),
            inverseJoinColumns = @JoinColumn(name = "language_code"))
    Set<Language> languages;// Ngoại Ngữ
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Set<CommendationAndDiscipline> commendations = new HashSet<>();

    @OneToMany(mappedBy = "employeeHistory", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Set<EmployeeHistory> employeeHistories = new HashSet<>();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contract_id", referencedColumnName = "id")
    Contract contract;//Hợp Đồng
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    private Department department;//Phòng Ban Làm Việc Của Nhân Viên

//    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    Set<Leave> leaves;

//    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    Set<PaymentSalary> paymentSalaries;

    //    @ManyToMany(mappedBy = "employees",cascade = {CascadeType.MERGE,CascadeType.PERSIST})
//    private Set<Position> positions;//Chức Vụ
    //@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "tbl_employee_position", joinColumns = @JoinColumn(name = "employee_code"),
            inverseJoinColumns = @JoinColumn(name = "position_code"))
    private Set<Position> positions = new HashSet<>();//Chức Vụ
    //
//    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<TimeKeeping> timeKeepings;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    private Certificate certificate;// Văn Bằng
    //
//    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Salary> salaries;
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<TimeKeeping> timeKeepings;

    // @OneToOne(cascade = CascadeType.ALL)
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "candidate_profile_id", referencedColumnName = "id")
    CandidateProfile candidateProfile;//Hợp Đồng
}
