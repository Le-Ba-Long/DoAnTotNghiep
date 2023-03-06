package com.longkubi.qlns.repository;

import com.longkubi.qlns.model.entity.CandidateProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CandidateProfileRepository extends JpaRepository<CandidateProfile, UUID> {
    Boolean existsCandidateProfileById(UUID id);

    Boolean existsCandidateProfileByCode(String code);

    @Query("select count (e) from CandidateProfile e where e.code = :code and e.id<>:id")
    Integer exclusionCode(@Param("code") String code, UUID id);

    //    @Query("select e from CandidateProfile e ORDER BY e.dateCreated DESC  ")
//    List<CandidateProfile> getAll();
//    @QueryHints(value = {@QueryHint(name = "javax.persistence.cache.storeMode", value = "USE")},
//            forCounting = false)
    @Query("SELECT c FROM CandidateProfile c ORDER BY c.dateCreated DESC ")
    List<CandidateProfile> getAll();
    //Stream<CandidateProfile> getAll();


    CandidateProfile getCandidateProfileById(UUID id);
//    @Query("select c from CandidateProfile c join Recruit r on c.recruit = :recruit having r.quantity - count(c.id) > 0")
//    Recruit getQuantityRecruitById(@Param(":recruit") Recruit recruit);

}
