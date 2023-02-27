package com.longkubi.qlns.repository;

import com.longkubi.qlns.model.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    Boolean existsDepartmentById(UUID id);

    Boolean existsDepartmentByCode(String code);

    Boolean existsDepartmentByName(String name);

    @Query("select count (e) from Department e where e.code = :code and e.id<>:id")
    Integer exclusionCode(@Param("code") String code, UUID id);

    @Query("select count (e) from Department e where e.name = :name and e.id<>:id")
    Integer exclusionName(@Param("name") String name, UUID id);

    Department getDepartmentById(UUID id);

    @Query("select e from Department e ORDER BY e.dateCreated DESC  ")
    List<Department> getAll();
}
