package com.longkubi.qlns.service;

import com.longkubi.qlns.model.dto.CertificateDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.CertificateSearchDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ICertificateService {

    ResponseData<CertificateDto> create(CertificateDto certificateDto, String token);

    ResponseData<CertificateDto> update(CertificateDto certificateDto, UUID id, String token);

    ResponseData<List<CertificateDto>> getAll();

    ResponseData<Page<CertificateDto>> searchByDto(CertificateSearchDto searchDto);

    ResponseData<Boolean> deleteById(UUID id);

}
