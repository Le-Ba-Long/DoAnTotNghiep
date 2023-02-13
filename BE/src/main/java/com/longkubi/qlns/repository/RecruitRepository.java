package com.longkubi.qlns.repository;

import com.longkubi.qlns.model.entity.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface RecruitRepository extends JpaRepository<Recruit, UUID> {
    Boolean existsRecruitById(UUID id);

    Boolean existsRecruitByCode(String code);

    @Query("select count (e) from Recruit e where e.code = :code and e.id<>:id")
    Integer exclusionCode(@Param("code") String code, UUID id);

    Recruit getRecruitById(UUID id);

    @Query("select e from Recruit e where e.status = 4 ")
    List<Recruit> getAllApprovalRecruit();

    @Query("select  e from Recruit e where e.id in (:listid)")
    Set<Recruit> getAllRecruit(@Param("listid") List<UUID> listIds);

    @Query("select e from Recruit e ORDER BY e.dateCreated ASC  ")
    List<Recruit> getAll();
//    @Query("select  r  from Recruit r where r.id = :id having r.quantity - r.candidateProfiles.size > 0")
//    Recruit getQuantityRecruitById(@Param("id") UUID id);

}
