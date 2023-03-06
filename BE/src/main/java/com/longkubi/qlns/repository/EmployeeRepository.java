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

    @Query("select e from Employee e ORDER BY e.dateCreated DESC  ")
    List<Employee> getAll();

    @Query("select e from Employee e where MONTH(e.dateOfBirth) between :startMonth and  :endMonth ORDER BY e.dateOfBirth ASC")
    List<Employee> getEmployeeBirthdayInRange(@Param("startMonth") int startMonth, @Param("endMonth") int endMonth);//lấy ra danh sách nhân viên có sinh nhật trong tháng nhập vào

    @Query("select e from Employee e  where  e.contract is  null and e.status = 3  ORDER BY e.dateCreated ASC  ")
    List<Employee> getEmployeesWithoutContract();

    @Query("SELECT MONTH(e.dateChange), YEAR(e.dateChange), COUNT(e.id) FROM Employee e WHERE e.status = 14 GROUP BY CONCAT(MONTH(e.dateChange), '-', YEAR(e.dateChange)) order by e.dateChange ASC")
    List<Object[]> personnelRecruitmentReport();// báo cáo biến động nhân sự tiếp nhận

    @Query("SELECT MONTH(e.dateChange),YEAR(e.dateChange), COUNT(e.id) FROM Employee e WHERE e.status = 15 GROUP BY CONCAT(MONTH(e.dateChange), '-', YEAR(e.dateChange)) order by e.dateChange ASC")
    List<Object[]> personnelAttritionReport();// báo cáo biến động tiếp nhận nhân sự nghỉ việc

    @Query("SELECT d.name,count(e.id) from Department d  left JOIN Employee e on d.id = e.department.id group by d.id")
    List<Object[]> getEmployeeCountByDepartment();// báo cáo tỉ lệ  nhân sự phân bổ theo từng phòng ban

    @Query("SELECT MONTH(e.dateChange),YEAR(e.dateChange), COUNT(e.id) FROM Employee e WHERE e.status in(12,14) GROUP BY CONCAT(MONTH(e.dateChange), '-', YEAR(e.dateChange)) order by e.dateChange ASC")
    List<Object[]> monthlyEmployeeCountReport();// báo cáo tổng số lượng nhân viên của công ty theo từng tháng (cả nghỉ việc,thử việc ,nhân viên chính thức)
}
