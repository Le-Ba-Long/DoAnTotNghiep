package com.longkubi.qlns.repository;

import com.longkubi.qlns.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    Boolean existsEmployeeById(UUID id);

    Boolean existsEmployeeByCode(String code);

    @Query("select count (e) from Employee e where e.code = :code and e.id<>:id")
    Integer exclusionCode(@Param("code") String code, UUID id);

    Employee getEmployeeById(UUID id);

    @Query("select e from Employee e ORDER BY e.dateCreated ASC  ")
    List<Employee> getAll();
    @Query("select e from Employee e  where  e.contract is  null and e.status = 3  ORDER BY e.dateCreated ASC  ")
    List<Employee> getEmployeesWithoutContract();
}
