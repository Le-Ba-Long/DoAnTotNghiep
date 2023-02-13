package com.longkubi.qlns.service;

import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.TimeKeepingDto;
import com.longkubi.qlns.model.dto.search.TimeKeepingSearchDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ITimeKeepingService {
    ResponseData<TimeKeepingDto> create(TimeKeepingDto timeKeepingDto, String token);

    ResponseData<TimeKeepingDto> update(TimeKeepingDto timeKeepingDto, UUID id, String token);

    ResponseData<List<TimeKeepingDto>> getAll();

    ResponseData<Page<TimeKeepingDto>> searchByDto(TimeKeepingSearchDto searchDto);
    ResponseData<Boolean> deleteById(UUID id);
}
