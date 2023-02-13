package com.longkubi.qlns.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_candidate_profile")
public class CandidateProfile implements Serializable {
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

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "age")
    private int age;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "education")
    private String education;//Trình Độ Học Vấn

    @Column(name = "major")
    private String major;//chuyên Ngành

    @Column(name = "image")
    private String image;// ảnh thẻ

    @Column(name = "imageimageName")
    private String imageName;//Tên ảnh thẻ

    @Column(name = "interviewer")
    private String interviewer;// người hẹn phỏng  vấn

    @Column(name = "interview_date")
    private Date interviewDate;//ngày hẹn phỏng vấn

    @Column(name = "refusal_reason")
    private String refusalReason;// lý do từ chối

    @Column(name = "status")
    private Integer status;

    @Column(name = "creator")
    String creator;

    @Column(name = "date_created")
    Date dateCreated;

    @Column(name = "changed_by")
    String changedBy;

    @Column(name = "date_change")
    Date dateChange;

    //@ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    // @JoinColumn(name = "recruit_code")
    //   @ManyToMany(mappedBy = "candidateProfiles",cascade = CascadeType.ALL)


    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "tbl_recruit_candidateProfile",
            joinColumns = @JoinColumn(name = "candidateProfile_id"),
            inverseJoinColumns = @JoinColumn(name = "recruit_id"))
    private Set<Recruit> recruit = new HashSet<>();
}
