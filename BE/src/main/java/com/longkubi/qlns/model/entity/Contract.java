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
@Table(name = "tbl_contract")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Contract implements Serializable {
    @Transient
    private static final long serialVersionUID = 4559994432567537044L;
    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;
    @Column(name = "code")
    private String code;//mã hợp đồng

    // @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "contract", cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    private Employee employee;//nhân viên

    @Column(name = "signing_date")
    private Date signingDate;// ngày kí hợp đồng

    @Column(name = "contract_effect")
    private Date contractEffect;// hiệu lực hợp đồng

    @Column(name = "status")
    private byte status;//trạng thái

    @Column(name = "basic_salary")
    private int basicSalary;// mức lương cơ bản

    @Column(name = "name_leader")
    private String nameLeader;// tên người lập hợp đồng

    @Column(name = "position_leader")
    private String postionLeader;// chức vụ người lập hợp đồng

    @Column(name = "hourly_rate")
    private int hourlyRate;//Số tiền tính cho 1h làm thêm

    @Column(name = "coefficient_salary")
    private float coefficientSalary; //Hệ Số Lương

    @Column(name = "creator")
    String creator;

    @Column(name = "date_created")
    Date dateCreated;

    @Column(name = "changed_by")
    String changedBy;

    @Column(name = "date_change")
    Date dateChange;
}
