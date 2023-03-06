package com.longkubi.qlns.service;

import com.longkubi.qlns.model.dto.EmployeeDto;
import com.longkubi.qlns.model.dto.PersonnelChangeReport;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.EmployeeSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IEmployeeService {
    ResponseData<EmployeeDto> create(EmployeeDto employeeDto, String token);

    ResponseData<EmployeeDto> update(EmployeeDto employeeDto, UUID id, String token);

    ResponseData<List<EmployeeDto>> getAll();

    ResponseData<List<EmployeeDto>> getEmployeesWithoutContract();

    //  ResponseData<Map<YearMonth, Integer>> getPersonnelChangeReport();
    ResponseData<List<PersonnelChangeReport>> getPersonnelChangeReport();

    ResponseData<List<PersonnelChangeReport>> getMonthlyEmployeeCountReport();

    ResponseData<List<PersonnelChangeReport>> getEmployeeCountByDepartment();

    ResponseData<List<EmployeeDto>> getEmployeeBirthdayInRange(int startMonth, int endMonth);

    ResponseData<Page<EmployeeDto>> searchByDto(EmployeeSearchDto searchDto);

    ResponseData<List<EmployeeDto>> getEmployeesAboutToExpireContract(Set<Integer> conditions);

    ResponseData<Boolean> deleteById(UUID id);

}
