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
@Table(name = "tbl_recruit")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Recruit implements Serializable {
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
    @Column(name = "title_recruit")
    private String titleRecruit;//Tiêu Đề Job Tuyển Dụng

    //@OneToMany(mappedBy = "recruit", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
//    @ManyToMany(fetch = FetchType.LAZY, cascade = {
//            CascadeType.PERSIST,
//            CascadeType.MERGE
//    })
//    @JoinTable(
//            name = "tbl_recruit_candidateProfile",
//            joinColumns = @JoinColumn(name = "recruit_id"),
//            inverseJoinColumns = @JoinColumn(name = "candidateProfile_id"))
    @ManyToMany(mappedBy = "recruit")
    private Set<CandidateProfile> candidateProfiles = new HashSet<>();
    @Column(name = "description")
    @Lob
    private String description;//Mô tả(phòng ban vị trí tuyển dụng)
    @Column(name = "require_recruit")
    @Lob
    private String requireRecruit;//Yêu Cầu Tuyển Dụng
    @Column(name = "benefits_received")
    @Lob
    private String benefitsReceived;//Quyền Lợi Nhận Được
    @Column(name = "quantity")
    private byte quantity;
    @Column(name = "recruitment_channel", columnDefinition = "nvarchar(255)")
    private String recruitmentChannel;//Kênh Tuyển Dụng
    @Column(name = "feedback")
    private String feedback;//Ý kiến từ chối
    @Column(name = "status")
    private byte status;
    @Column(name = "creator")
    private String creator;
    @Column(name = "date_created")
    private Date dateCreated;
    @Column(name = "changed_by")
    private String changedBy;
    @Column(name = "date_change")
    private Date dateChange;


}
