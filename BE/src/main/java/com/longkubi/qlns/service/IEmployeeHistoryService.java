package com.longkubi.qlns.service;

import com.longkubi.qlns.model.dto.EmployeeHistoryDto;
import com.longkubi.qlns.model.dto.ResponseData;

import java.util.List;
import java.util.UUID;

public interface IEmployeeHistoryService {
    ResponseData<List<EmployeeHistoryDto>> getAll();

    ResponseData<EmployeeHistoryDto> create(EmployeeHistoryDto employeeHistoryDto);

    ResponseData<List<EmployeeHistoryDto>> getEmployeeHistoryByID(UUID employeeID);

}
