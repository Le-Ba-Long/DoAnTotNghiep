package com.longkubi.qlns.repository;

import com.longkubi.qlns.model.entity.Employee;
import com.longkubi.qlns.model.entity.Language;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LanguageRepository extends JpaRepository<Language, UUID> {
    Boolean existsLanguageById(UUID id);
    Boolean existsLanguageByCode(String code);
    Boolean existsLanguageByName(String name);
    @Query("select count (e) from Language e where e.code = :code and e.id<>:id")
    Integer exclusionCode(@Param("code") String code,UUID id);
    @Query("select count (e) from Language e where e.name = :name and e.id<>:id")
    Integer exclusionName(@Param("name") String name,UUID id);
    Language getLanguageById(UUID id);
    @Query("select e from Language e ORDER BY e.dateCreated ASC  ")
    List<Language> getAll();

}
