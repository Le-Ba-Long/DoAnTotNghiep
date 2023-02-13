package com.longkubi.qlns.repository;

import com.longkubi.qlns.model.entity.Position;
import com.longkubi.qlns.model.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface SalaryRepository extends JpaRepository<Salary, UUID> {
    Boolean existsSalaryById(UUID id);

    Boolean existsSalaryByCode(String code);

    @Query("select count (e) from Salary e where e.code = :code and e.id<>:id")
    Integer exclusionCode(@Param("code") String code, UUID id);

    Salary getSalaryById(UUID id);
}