package com.longkubi.qlns.service;

import com.longkubi.qlns.model.dto.CommendationAndDisciplineDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.CommendationAndDisciplineSearchDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ICommendationAndDisciplineService {
    ResponseData<CommendationAndDisciplineDto> create(CommendationAndDisciplineDto timeKeepingDto, String token);

    ResponseData<CommendationAndDisciplineDto> update(CommendationAndDisciplineDto timeKeepingDto, UUID id, String token);

    ResponseData<List<CommendationAndDisciplineDto>> getAll();

    ResponseData<Page<CommendationAndDisciplineDto>> searchByDto(CommendationAndDisciplineSearchDto searchDto);

    ResponseData<Boolean> deleteById(UUID id);
}
