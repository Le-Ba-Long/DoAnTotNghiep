package com.longkubi.qlns.service;

import com.longkubi.qlns.model.dto.DepartmentDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.DepartmentSearchDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface IDepartmentService {
    ResponseData<DepartmentDto> create(DepartmentDto departmentDto, String token);
    ResponseData<DepartmentDto> update(DepartmentDto departmentDto, UUID id, String token);
    ResponseData<List<DepartmentDto>> getAll();

    ResponseData<Page<DepartmentDto>> searchByDto(DepartmentSearchDto searchDto);

    ResponseData<Boolean> deleteById(UUID id, String token);
}
