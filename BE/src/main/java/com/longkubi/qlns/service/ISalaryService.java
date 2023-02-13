package com.longkubi.qlns.service;

import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.SalaryDto;
import com.longkubi.qlns.model.dto.search.SalarySearchDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ISalaryService {
    ResponseData<SalaryDto> create(SalaryDto salaryDto, String token);

    ResponseData<SalaryDto> update(SalaryDto salaryDto, UUID id, String token);

    ResponseData<List<SalaryDto>> getAll();

    ResponseData<Page<SalaryDto>> searchByDto(SalarySearchDto searchDto);


    ResponseData<Boolean> deleteById(UUID id, String token);
}
