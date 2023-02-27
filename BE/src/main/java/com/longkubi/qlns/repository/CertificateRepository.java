package com.longkubi.qlns.repository;

import com.longkubi.qlns.model.entity.CandidateProfile;
import com.longkubi.qlns.model.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, UUID> {
    Boolean existsCertificateById(UUID id);

    Boolean existsCertificateByCode(String code);

    Boolean existsCertificateByName(String name);

    @Query("select count (e) from Certificate e where e.code = :code and e.id<>:id")
    Integer exclusionCode(@Param("code") String code, UUID id);

    @Query("select count (e) from Certificate e where e.name = :name and e.id<>:id")
    Integer exclusionName(@Param("name") String name, UUID id);

    @Query("select e from Certificate e ORDER BY e.dateCreated desc  ")
    List<Certificate> getAll();

    Certificate getCertificateById(UUID id);

}
