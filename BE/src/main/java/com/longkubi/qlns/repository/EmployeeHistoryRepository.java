package com.longkubi.qlns.repository;

import com.longkubi.qlns.model.entity.EmployeeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface EmployeeHistoryRepository extends JpaRepository<EmployeeHistory, UUID> {
    @Query("select h from EmployeeHistory h JOIN  Employee e on e.id = h.employeeHistory.id   ORDER BY h.date ASC")
    List<EmployeeHistory> getEmployeeHistoryByID(UUID employeeID);
}
