package com.longkubi.qlns.repository;

import com.longkubi.qlns.model.entity.CommendationAndDiscipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommendationAndDisciplineRepository extends JpaRepository<CommendationAndDiscipline, UUID> {
    Boolean existsCommendationAndDisciplineById(UUID id);


    @Query("select e from CommendationAndDiscipline e ORDER BY e.dateCreated DESC  ")
    List<CommendationAndDiscipline> getAll();

    CommendationAndDiscipline getCommendationAndDisciplineById(UUID id);
    //CommendationAndDiscipline getCommendationAndDisciplineById(UUID id);

}
