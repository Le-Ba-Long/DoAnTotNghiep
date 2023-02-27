package com.longkubi.qlns.service.impl;

import com.longkubi.qlns.common.Constant;
import com.longkubi.qlns.common.ErrorMessage;
import com.longkubi.qlns.model.dto.RecruitDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.RecruitSearchDto;
import com.longkubi.qlns.model.entity.CandidateProfile;
import com.longkubi.qlns.model.entity.Recruit;
import com.longkubi.qlns.repository.RecruitRepository;
import com.longkubi.qlns.security.jwt.JwtProvider;
import com.longkubi.qlns.service.IRecruitService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static com.longkubi.qlns.common.ErrorMessage.*;

@Transactional
@Service
public class RecruitServiceImpl implements IRecruitService {
    @Autowired
    private RecruitRepository repo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private EntityManager manager;

    @Override
    public ResponseData<RecruitDto> create(RecruitDto recruitDto, String token) {
        ErrorMessage errorMessage = validateRecruit(recruitDto, null, Constant.Insert);
        if (!errorMessage.equals(SUCCESS)) return new ResponseData<>(errorMessage, null);
        Recruit entity = new Recruit();
        modelMapper.map(recruitDto, entity);
        entity.setCreator(jwtProvider.getUserNameFromToken(token));
        entity.setDateCreated(new Date());
        //    entity.setCandidateProfiles(recruitDto.getCandidateProfiles().stream().map(dto->modelMapper.map(dto,CandidateProfile.class)).collect(Collectors.toSet()));
        return new ResponseData<>(modelMapper.map(repo.save(entity), RecruitDto.class));
    }

    @Override
    public ResponseData<RecruitDto> update(RecruitDto recruitDto, UUID id, String token) {
        ErrorMessage errorMessage = validateRecruit(recruitDto, id, Constant.Update);
        if (!errorMessage.equals(SUCCESS)) return new ResponseData<>(errorMessage, null);
        Recruit entity = repo.getRecruitById(id);
        entity.setCode(recruitDto.getCode());
        entity.setCandidateProfiles(recruitDto.getCandidateProfiles().stream().map(dto -> modelMapper.map(dto, CandidateProfile.class)).collect(Collectors.toSet()));
        entity.setDescription(recruitDto.getDescription());
        entity.setStatus(recruitDto.getStatus());
        entity.setTitleRecruit(recruitDto.getTitleRecruit());
        entity.setBenefitsReceived(recruitDto.getBenefitsReceived());
        entity.setQuantity(recruitDto.getQuantity());
        entity.setRecruitmentChannel(recruitDto.getRecruitmentChannel());
        entity.setFeedback(recruitDto.getFeedback());
        entity.setChangedBy(jwtProvider.getUserNameFromToken(token));
        entity.setDateChange(new Date());
        return new ResponseData<>(modelMapper.map(repo.save(entity), RecruitDto.class));
    }

    @Override
    public ResponseData<?> updateStatus(RecruitDto recruitDto, UUID id, String token) {
        Recruit entity = null;
        if (Boolean.FALSE.equals(repo.existsRecruitById(id)))
            return new ResponseData<>(ID_NOT_EXIST, null);
        if (recruitDto.getStatus() == Constant.StatusType.FIX_REQUEST.getType()) {
            entity = repo.getRecruitById(id);
            entity.setFeedback(recruitDto.getFeedback());
            entity.setStatus(recruitDto.getStatus());
        }
        if (recruitDto.getStatus() == Constant.StatusType.PROCESSING.getType()
                || recruitDto.getStatus() == Constant.StatusType.REJECT.getType()
                || recruitDto.getStatus() == Constant.StatusType.FIX_REQUEST.getType()) {
            entity = repo.getRecruitById(id);
            entity.setStatus(recruitDto.getStatus());
            return new ResponseData<>(SUCCESS, modelMapper.map(entity, RecruitDto.class));
        }
        return new ResponseData<>(STATUS_NOT_SUPPORT, null);
    }

    @Override
    public ResponseData<List<RecruitDto>> getAll() {
        //  List<Recruit> recruits = repo.findAll();
        List<Recruit> recruits = repo.getAll();
        if (recruits.isEmpty()) return new ResponseData<>(LIST_IS_EMPTY, null);
        return new ResponseData<>(recruits.stream().map(dto -> modelMapper.map(dto, RecruitDto.class)).collect(Collectors.toList()));
    }

    /**
     * Hàm Tìm Kiếm Chức Vụ Theo Điều Kiện Tìm Kiếm Truyền Vào
     *
     * @param searchDto
     * @return ResponseData'<'Page<'DepartmentDto'>>'
     */
    @Override
    public ResponseData<Page<RecruitDto>> searchByDto(RecruitSearchDto searchDto) {
        if (Objects.isNull(searchDto)) return new ResponseData<>(OBJECT_CANNOT_EMPTY, null);
        Integer pageIndex = searchDto.getPageIndex();
        Integer pageSize = searchDto.getPageSize();
        if (pageIndex > 0) {
            pageIndex--;
        } else {
            pageIndex = 0;
        }
        StringBuilder sqlCount = new StringBuilder("SELECT COUNT(entity.id) FROM Recruit entity WHERE (1=1) ");
        StringBuilder sql = new StringBuilder("SELECT entity FROM Recruit entity WHERE (1=1) ");
        String whereClause = genWhereClause(searchDto);
        String orderBy = genOrderByClause(searchDto);
        sql.append(whereClause).append(orderBy);
        sqlCount.append(whereClause);
        Query queryCount = manager.createQuery(sqlCount.toString());
        Query querySql = manager.createQuery(sql.toString());
        setParameter(queryCount, searchDto);
        setParameter(querySql, searchDto);

        int startPosition = pageIndex * pageSize;
        querySql.setFirstResult(startPosition);
        querySql.setMaxResults(pageSize);
        List<Recruit> positionDtoList = querySql.getResultList();
        long count = (long) queryCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        // return new ResponseData<>(new PageImpl<>(departmentList, pageable, count));
        Page<RecruitDto> result = new PageImpl<>(positionDtoList.stream().map(RecruitDto::convertFromEntityToDto).collect(Collectors.toList()));
        if (result.isEmpty()) return new ResponseData<>(LIST_IS_EMPTY, null);
        return new ResponseData<>(result);
    }


    private String genOrderByClause(RecruitSearchDto dto) {
        if (dto.getOrderByFilter() != null && StringUtils.hasText(dto.getOrderByFilter())) {
            switch (dto.getOrderByFilter()) {
                case "Code ASC":
                    return "ORDER BY entity.code ASC";
                case "Code DESC":
                    return "ORDER BY entity.code DESC";
                case "DateCreated ASC":
                    return "ORDER BY entity.dateCreated ASC";
                case "DateCreated DESC":
                    return "ORDER BY entity.dateCreated DESC";
                case "DateChange ASC":
                    return "ORDER BY entity.dateChange ASC";
                case "DateChange DESC":
                    return "ORDER BY entity.dateChange DESC";
            }
        }
        return "";
    }

    private void setParameter(Query query, RecruitSearchDto searchDto) {
        if (!Objects.isNull(searchDto.getId())) {
            query.setParameter("id", searchDto.getId());
        }
        if (StringUtils.hasText(searchDto.getCode())) {
            query.setParameter("code", searchDto.getCode());
        }
        if (StringUtils.hasText(searchDto.getDescription())) {
            query.setParameter("description", searchDto.getDescription());
        }
        if (!Objects.isNull(searchDto.getCandidateProfiles())) {
            query.setParameter("candidateProfiles", searchDto.getCandidateProfiles());
        }
        if (StringUtils.hasText(searchDto.getCreator())) {
            query.setParameter("creator", searchDto.getCreator());
        }
        if (!Objects.isNull(searchDto.getDateCreated())) {
            Date createdStartDate = new Date();
            createdStartDate.setHours(0);
            createdStartDate.setMinutes(0);
            createdStartDate.setSeconds(0);
            createdStartDate.setDate(searchDto.getDateCreated().getDate());
            createdStartDate.setMonth(searchDto.getDateCreated().getMonth());
            createdStartDate.setYear(searchDto.getDateCreated().getYear());
            query.setParameter("dateCreated", createdStartDate);
        }
        if (StringUtils.hasText(searchDto.getChangedBy())) {
            query.setParameter("changeBy", searchDto.getChangedBy());
        }
        if (!Objects.isNull(searchDto.getDateChange())) {
            Date changeStartDate = new Date();
            changeStartDate.setHours(0);
            changeStartDate.setMinutes(0);
            changeStartDate.setSeconds(0);
            changeStartDate.setDate(searchDto.getDateChange().getDate());
            changeStartDate.setMonth(searchDto.getDateChange().getMonth());
            changeStartDate.setYear(searchDto.getDateChange().getYear());
            query.setParameter("dateChange", changeStartDate);
        }
        //  return query;
    }

    private String genWhereClause(RecruitSearchDto searchDto) {
        StringBuilder whereClause = new StringBuilder();
        if (!Objects.isNull(searchDto.getId())) {
            whereClause.append(" AND (entity.id = :id)");
        }
        if (StringUtils.hasText(searchDto.getCode())) {
            whereClause.append(" AND (entity.code  = :code)");
        }
        if (!Objects.isNull(searchDto.getCandidateProfiles())) {
            whereClause.append(" AND (entity.candidateProfiles = :candidateProfiles)");
        }
        if (StringUtils.hasText(searchDto.getDescription())) {
            whereClause.append(" AND (entity.description = :description)");
        }
        if (!Objects.isNull(searchDto.getStatus())) {
            whereClause.append(" AND (entity.status = :status)");
        }
        if (StringUtils.hasText(searchDto.getCreator())) {
            whereClause.append(" AND (entity.creator = :creator)");
        }
        if (!Objects.isNull(searchDto.getDateCreated())) {
            whereClause.append(" AND (entity.dateCreated = :dateCreated)");
        }
        if (!Objects.isNull(searchDto.getDateChange())) {
            whereClause.append(" AND (entity.dateChange = :dateChange)");
        }
        if (StringUtils.hasText(searchDto.getChangedBy())) {
            whereClause.append(" AND (entity.changedBy = :changedBy)");
        }
        return whereClause.toString();
    }

    @Override
    public ResponseData<Boolean> deleteById(UUID id) {
        if (Boolean.TRUE.equals(repo.existsRecruitById(id))) {
            repo.deleteById(id);
            return new ResponseData<>(SUCCESS, true);
        } else {
            return new ResponseData<>(ID_NOT_EXIST, false);
        }
    }

    @Override
    public ResponseData<List<RecruitDto>> getAllApprovalRecruit() {
        return new ResponseData<>(SUCCESS, repo.getAllApprovalRecruit().stream().map(recruit -> modelMapper.map(recruit, RecruitDto.class)).collect(Collectors.toList()));
    }


    @Override
    public Set<Recruit> getAllRecruit(List<UUID> listIds) {
        return repo.getAllRecruit(listIds);
    }

    @Override
    public Recruit getRecruitById(UUID id) {
        return repo.getRecruitById(id);
    }

    private ErrorMessage validateRecruit(RecruitDto recruitDto, UUID id, String action) {
        if (Constant.Insert.equals(action)) {
            if (Objects.isNull(recruitDto)) return OBJECT_CANNOT_EMPTY;
            if (Boolean.TRUE.equals(repo.existsRecruitByCode(recruitDto.getCode())))
                return CODE_ALREADY_EXIST;
        } else {
            if (Objects.isNull(recruitDto)) return OBJECT_CANNOT_EMPTY;
            if (repo.exclusionCode(recruitDto.getCode(), id) > 0) return CODE_ALREADY_EXIST;

        }
        return SUCCESS;
    }

}
