package com.longkubi.qlns.service;

import com.longkubi.qlns.model.dto.ContractDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.ContractSearchDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface IContractService {

    ResponseData<ContractDto> create(ContractDto contractDto, String token);

    ResponseData<ContractDto> update(ContractDto contractDto, UUID id, String token);

    ResponseData<List<ContractDto>> getAll();

    ResponseData<Page<ContractDto>> searchByDto(ContractSearchDto searchDto);

    ResponseData<Boolean> deleteById(UUID id);
    ContractDto getContractById(UUID id);

    ResponseData<ContractDto> getContractByEmployeeId(UUID id);

}
