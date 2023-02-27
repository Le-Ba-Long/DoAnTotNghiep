package com.longkubi.qlns.service;

import com.longkubi.qlns.model.dto.EmployeeDto;
import com.longkubi.qlns.model.dto.PersonnelChangeReport;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.EmployeeSearchDto;
import org.springframework.data.domain.Page;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;
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

    ResponseData<Page<EmployeeDto>> searchByDto(EmployeeSearchDto searchDto);

    ResponseData<Boolean> deleteById(UUID id);

}
