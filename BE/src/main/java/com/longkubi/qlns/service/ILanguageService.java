package com.longkubi.qlns.service;

import com.longkubi.qlns.model.dto.LanguageDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.LanguageSearchDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ILanguageService {
    ResponseData<LanguageDto> create(LanguageDto languageDto, String token);

    ResponseData<LanguageDto> update(LanguageDto languageDto, UUID id, String token);

    ResponseData<List<LanguageDto>> getAll();

    ResponseData<Page<LanguageDto>> searchByDto(LanguageSearchDto searchDto);

    ResponseData<Boolean> deleteById(UUID id, String token);
}
