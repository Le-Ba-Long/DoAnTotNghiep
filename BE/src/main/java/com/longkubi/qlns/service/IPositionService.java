package com.longkubi.qlns.service;

import com.longkubi.qlns.model.dto.PositionDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.PositionSearchDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IPositionService {
    ResponseData<PositionDto> create(PositionDto positionDto, String token);

    ResponseData<PositionDto> update(PositionDto positionDto, UUID id, String token);

    ResponseData<List<PositionDto>> getAll();

    ResponseData<Page<PositionDto>> searchByDto(PositionSearchDto searchDto);

    ResponseData<Boolean> deleteById(UUID id, String token);

    PositionDto getPositionById(UUID id);
    Set<PositionDto> getPositionByListId(Set<UUID> ids);
}
