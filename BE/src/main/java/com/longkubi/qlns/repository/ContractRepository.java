package com.longkubi.qlns.repository;

import com.longkubi.qlns.model.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;

import java.util.List;
import java.util.UUID;

public interface ContractRepository extends JpaRepository<Contract, UUID> {

    Boolean existsContractById(UUID id);

    Boolean existsContractByCode(String code);

    @Query("select count (e) from Contract e where e.code = :code and e.id <> :id")
    Integer exclusionCode(@Param("code") String code, @Param("id") UUID id);

    Contract getContractById(UUID id);

    @Query("select c from Contract c  join Employee e on e.id = c.employee.id  where e.id = :employeeId ")
    Contract getContractByEmployeeId(@Param("employeeId") UUID id);

    @Query("select e from Contract e ORDER BY e.dateCreated desc  ")
    List<Contract> getAll();
}
