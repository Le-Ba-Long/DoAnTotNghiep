package com.longkubi.qlns.service;

import com.longkubi.qlns.model.dto.RecruitDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.RecruitSearchDto;
import com.longkubi.qlns.model.entity.Recruit;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IRecruitService {
    ResponseData<RecruitDto> create(RecruitDto recruitDto, String token);

    ResponseData<RecruitDto> update(RecruitDto recruitDto, UUID id, String token);
    ResponseData<?> updateStatus(RecruitDto recruitDto, UUID id, String token);

    ResponseData<List<RecruitDto>> getAll();

    ResponseData<Page<RecruitDto>> searchByDto(RecruitSearchDto searchDto);

    ResponseData<Boolean> deleteById(UUID id);
    ResponseData<List<RecruitDto>>getAllApprovalRecruit();
    Set<Recruit> getAllRecruit(List<UUID>listIds);

    Recruit getRecruitById(UUID id);

}
