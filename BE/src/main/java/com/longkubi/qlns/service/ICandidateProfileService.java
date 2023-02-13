package com.longkubi.qlns.service;

import com.longkubi.qlns.model.dto.CandidateProfileDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.CandidateProfileSearchDto;
import com.longkubi.qlns.model.dto.search.CertificateSearchDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ICandidateProfileService {
    ResponseData<CandidateProfileDto> create(CandidateProfileDto candidateProfileDto, String token);

    ResponseData<CandidateProfileDto> update(CandidateProfileDto candidateProfileDto, UUID id, String token);

    ResponseData<List<CandidateProfileDto>> getAll();

    ResponseData<Page<CandidateProfileDto>> searchByDto(CandidateProfileSearchDto searchDto);

    ResponseData<Boolean> deleteById(UUID id);

}
