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
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_certificate")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Certificate implements Serializable {
    @Transient
    private static final long serialVersionUID = 4559994432567537044L;
    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;
    @Column(name = "code")
    private String code;// mã bằng cấp
    @Column(name = "name")
    private String name;//tên bằng cấp
    @Column(name = "majors")
    private String majors;//chuyên nghành
    @Column(name = "issued_date")
    private Date issuedDate;// ngày cấp
    @Column(name = "granted_by")
    private String grantedBy;// được cấp bởi
    @Column(name = "creator")
    String creator;
    @Column(name = "date_created")
    Date dateCreated;
    @Column(name = "changed_by")
    String changedBy;
    @Column(name = "date_change")
    Date dateChange;
    @OneToMany(mappedBy = "certificate", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Employee> employees;
}
