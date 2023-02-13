package com.longkubi.qlns.repository;

import com.longkubi.qlns.model.entity.TimeKeeping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TimeKeepingRepository extends JpaRepository<TimeKeeping, UUID> {

    Boolean existsTimeKeepingById(UUID id);

    Boolean existsTimeKeepingByCode(String code);

    @Query("select count (e) from TimeKeeping e where e.code = :code and e.id <> :id")
    Integer exclusionCode(@Param("code") String code, UUID id);

    TimeKeeping getTimeKeepingById(UUID id);

    @Query("select e from TimeKeeping e ORDER BY e.dateCreated ASC  ")
    List<TimeKeeping> getAll();

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END from TimeKeeping t where  t.employee.id = :employeeId and t.month = :month and t.year = :year")
    boolean existsTimeKeepingByMonthAndYear(@Param("month") int month, @Param("year") int year, @Param("employeeId") UUID employeeId);

    // public boolean isValidMonthYear(int month, int year);
    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END from TimeKeeping t where  t.id <> :id and t.month = :month and t.year = :year and t.employee.id = :employeeId")
    boolean isValidMonthYearExcludingCurrent(@Param("month") int month, @Param("year") int year, @Param("employeeId") UUID employeeId, @Param("id") UUID id);


}
