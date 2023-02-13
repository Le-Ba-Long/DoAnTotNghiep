package com.longkubi.qlns.repository;

import com.longkubi.qlns.model.entity.PaymentSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PaymentSalaryRepository extends JpaRepository<PaymentSalary, UUID> {
    Boolean existsPaymentSalaryById(UUID id);

    //Boolean existsPaymentSalaryByCode(String code);

//    @Query("select count (e) from PaymentSalary e where e.code = :code and e.id <> :id")
//    Integer exclusionCode(@Param("code") String code, UUID id);

    PaymentSalary getPaymentSalaryById(UUID id);

    @Query("select e from PaymentSalary e ORDER BY e.dateCreated ASC  ")
    List<PaymentSalary> getAll();

    @Query("select p from PaymentSalary p join TimeKeeping t " +
            "on p.timeKeeping.id = t.id JOIN Employee e on e.id = t.employee.id " +
            "where e.id = :employeeId ORDER BY p.dateCreated ASC  ")
    List<PaymentSalary> getAllPaymentSalaryByEmployeeId(@Param("employeeId") UUID employeeId);

}
