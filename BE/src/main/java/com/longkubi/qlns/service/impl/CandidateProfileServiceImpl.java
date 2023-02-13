package com.longkubi.qlns.service.impl;

import com.longkubi.qlns.common.Constant;
import com.longkubi.qlns.common.ErrorMessage;
import com.longkubi.qlns.model.dto.CandidateProfileDto;
import com.longkubi.qlns.model.dto.RecruitDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.CandidateProfileSearchDto;
import com.longkubi.qlns.model.entity.CandidateProfile;
import com.longkubi.qlns.model.entity.Recruit;
import com.longkubi.qlns.repository.CandidateProfileRepository;
import com.longkubi.qlns.security.jwt.JwtProvider;
import com.longkubi.qlns.service.ICandidateProfileService;
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

import static com.longkubi.qlns.common.Constant.StatusType.FINISHED;
import static com.longkubi.qlns.common.ErrorMessage.*;
import static com.longkubi.qlns.model.dto.CandidateProfileDto.convertFromEntityToDto;

@Transactional
@Service
public class CandidateProfileServiceImpl implements ICandidateProfileService {
    @Autowired
    private CandidateProfileRepository repo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private IRecruitService recruitService;

    @Autowired
    private EntityManager manager;

    @Override
    public ResponseData<CandidateProfileDto> create(CandidateProfileDto candidateProfileDto, String token) {
        ErrorMessage errorMessage = validateCandidateProfile(candidateProfileDto, null, Constant.Insert);
        if (!errorMessage.equals(SUCCESS)) return new ResponseData<>(errorMessage, null);
        CandidateProfile entity = new CandidateProfile();
//        MultipartFile multipartFile = candidateProfileDto.getImage();
//        String fileName = multipartFile.getOriginalFilename();
//        try {
//            FileCopyUtils.copy(candidateProfileDto.getImage().getBytes(), new File(this.fileUpload + fileName));
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }

        modelMapper.map(candidateProfileDto, entity);
        // entity.setImage(fileName);
        entity.setRecruit(recruitService.getAllRecruit(candidateProfileDto.getRecruitDtos().stream().map(recruitDto -> recruitDto.getId()).collect(Collectors.toList())));
        entity.setCreator(jwtProvider.getUserNameFromToken(token));
        entity.setDateCreated(new Date());
        Recruit recruit = recruitService.getRecruitById(candidateProfileDto.getRecruitDtos().get(0).getId());
        int quantity = recruit.getQuantity();
        int numberOfApplicants = recruit.getCandidateProfiles().size();
        if ((quantity - numberOfApplicants) == 1) {
            recruit.setStatus(FINISHED.getType());
        }
        return new ResponseData<>(convertFromEntityToDto(repo.save(entity)));
    }


    @Override
    public ResponseData<CandidateProfileDto> update(CandidateProfileDto candidateProfileDto, UUID id, String token) {
        ErrorMessage errorMessage = validateCandidateProfile(candidateProfileDto, id, Constant.Update);
        if (!errorMessage.equals(SUCCESS)) return new ResponseData<>(errorMessage, null);
        CandidateProfile entity = repo.getCandidateProfileById(id);
        entity.setCode(candidateProfileDto.getCode());
        entity.setFullName(candidateProfileDto.getFullName());
        entity.setDateOfBirth(candidateProfileDto.getDateOfBirth());
        entity.setAge(candidateProfileDto.getAge());
        entity.setAddress(candidateProfileDto.getAddress());
        entity.setEmail(candidateProfileDto.getEmail());
        entity.setPhone(candidateProfileDto.getPhone());
        entity.setEducation(candidateProfileDto.getEducation());
        entity.setMajor(candidateProfileDto.getMajor());
        entity.setStatus(candidateProfileDto.getStatus());
        entity.setRefusalReason(candidateProfileDto.getRefusalReason());
        entity.setInterviewer(candidateProfileDto.getInterviewer());
        entity.setInterviewDate(candidateProfileDto.getInterviewDate());
        entity.setImage(candidateProfileDto.getImage());
        entity.setImageName(candidateProfileDto.getImageName());
        //  entity.setRecruit(recruitService.getAllRecruit(candidateProfileDto.getRecruitDtos().stream().map(recruitDto -> recruitDto.getId()).collect(Collectors.toList())));
        Set<Recruit> recruits = recruitService.getAllRecruit(candidateProfileDto.getRecruitDtos().stream().map(RecruitDto::getId).collect(Collectors.toList()));
        entity.getRecruit().clear();
        entity.getRecruit().addAll(recruits);
        // Set<Recruit> recruit = recruitService.getAllRecruit(candidateProfileDto.getRecruitDtos().stream().map(RecruitDto::getId).collect(Collectors.toList()));
        //recruit.stream().forEach(dto ->dto.setCandidateProfiles(entity));

        //entity.setRecruit(candidateProfileDto.getRecruitDtos().stream().map(recruitDto -> recruitService.getRecruitById(recruitDto.getId())).collect(Collectors.toList()));
        entity.setChangedBy(jwtProvider.getUserNameFromToken(token));
        entity.setDateChange(new Date());
        Recruit recruit = recruitService.getRecruitById(candidateProfileDto.getRecruitDtos().get(0).getId());
        int quantity = recruit.getQuantity();
        int numberOfApplicants = recruit.getCandidateProfiles().size();
        if ((quantity - numberOfApplicants) == 1) {
            recruit.setStatus(FINISHED.getType());
        }
        CandidateProfile dto = repo.save(entity);
        return new ResponseData<>(convertFromEntityToDto(dto));
    }

    @Override
    public ResponseData<List<CandidateProfileDto>> getAll() {
        // List<CandidateProfile> candidateProfiles = repo.findAll();
        List<CandidateProfile> candidateProfiles = repo.getAll();
        if (candidateProfiles.isEmpty()) return new ResponseData<>(SUCCESS, new ArrayList<>());
        return new ResponseData<>(SUCCESS, candidateProfiles.stream().map(CandidateProfileDto::convertFromEntityToDto).collect(Collectors.toList()));
    }

    /**
     * Hàm Tìm Kiếm Chức Vụ Theo Điều Kiện Tìm Kiếm Truyền Vào
     *
     * @param searchDto
     * @return ResponseData'<'Page<'DepartmentDto'>>'
     */
    @Override
    public ResponseData<Page<CandidateProfileDto>> searchByDto(CandidateProfileSearchDto searchDto) {
        if (Objects.isNull(searchDto)) return new ResponseData<>(OBJECT_CANNOT_EMPTY, null);
        Integer pageIndex = searchDto.getPageIndex();
        Integer pageSize = searchDto.getPageSize();
        if (pageIndex > 0) {
            pageIndex--;
        } else {
            pageIndex = 0;
        }
        StringBuilder sqlCount = new StringBuilder("SELECT COUNT(entity.id) FROM CandidateProfile entity WHERE (1=1) ");
        StringBuilder sql = new StringBuilder("SELECT entity FROM CandidateProfile entity WHERE (1=1) ");
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
        List<CandidateProfile> positionDtoList = querySql.getResultList();
        long count = (long) queryCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        // return new ResponseData<>(new PageImpl<>(departmentList, pageable, count));
        Page<CandidateProfileDto> result = new PageImpl<>(positionDtoList.stream().map(CandidateProfileDto::convertFromEntityToDto).collect(Collectors.toList()));
        if (result.isEmpty()) return new ResponseData<>(LIST_IS_EMPTY, null);
        return new ResponseData<>(result);
    }


    private String genOrderByClause(CandidateProfileSearchDto dto) {
        if (dto.getOrderByFilter() != null && StringUtils.hasText(dto.getOrderByFilter())) {
            switch (dto.getOrderByFilter()) {
                case "Code ASC":
                    return "ORDER BY entity.code ASC";
                case "Code DESC":
                    return "ORDER BY entity.code DESC";
                case "Name ASC":
                    return "ORDER BY entity.fullName ASC";
                case "Name DESC":
                    return "ORDER BY entity.fullName DESC";
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

    private void setParameter(Query query, CandidateProfileSearchDto searchDto) {
        if (!Objects.isNull(searchDto.getId())) {
            query.setParameter("id", searchDto.getId());
        }
        if (StringUtils.hasText(searchDto.getCode())) {
            query.setParameter("code", searchDto.getCode());
        }
        if (StringUtils.hasText(searchDto.getFullName())) {
            query.setParameter("fullName", '%' + searchDto.getFullName() + '%');
        }
        if (!Objects.isNull(searchDto.getDateOfBirth())) {
            Date datdateOfBirth = new Date();
            datdateOfBirth.setHours(0);
            datdateOfBirth.setMinutes(0);
            datdateOfBirth.setSeconds(0);
            datdateOfBirth.setDate(searchDto.getDateOfBirth().getDate());
            datdateOfBirth.setMonth(searchDto.getDateOfBirth().getMonth());
            datdateOfBirth.setYear(searchDto.getDateOfBirth().getYear());
            query.setParameter("dateOfBirth", datdateOfBirth);
        }
        if (!Objects.isNull(searchDto.getAge())) {
            query.setParameter("age", searchDto.getAge());
        }
        if (StringUtils.hasText(searchDto.getAddress())) {
            query.setParameter("address", searchDto.getAddress());
        }
        if (StringUtils.hasText(searchDto.getEmail())) {
            query.setParameter("email", searchDto.getEmail());
        }
        if (StringUtils.hasText(searchDto.getPhone())) {
            query.setParameter("phone", searchDto.getPhone());
        }
        if (StringUtils.hasText(searchDto.getCreator())) {
        }
        if (StringUtils.hasText(searchDto.getEducation())) {
            query.setParameter("education", searchDto.getEducation());
        }
        if (StringUtils.hasText(searchDto.getMajor())) {
            query.setParameter("major", searchDto.getMajor());
        }
        if (!Objects.isNull(searchDto.getRecruit())) {
            query.setParameter("recruit", searchDto.getRecruit());
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

    private String genWhereClause(CandidateProfileSearchDto searchDto) {
        StringBuilder whereClause = new StringBuilder();
        if (!Objects.isNull(searchDto.getId())) {
            whereClause.append(" AND (entity.id = :id)");
        }
        if (StringUtils.hasText(searchDto.getCode())) {
            whereClause.append(" AND (entity.code  = :code)");
        }
        if (StringUtils.hasText(searchDto.getFullName())) {
            whereClause.append(" AND (entity.fullName LIKE :fullName)");
        }
        if (!Objects.isNull(searchDto.getDateOfBirth())) {
            whereClause.append(" AND (entity.dateOfBirth = :dateOfBirth)");
        }
        if (!Objects.isNull(searchDto.getAge())) {
            whereClause.append(" AND (entity.age = :age)");
        }
        if (StringUtils.hasText(searchDto.getAddress())) {
            whereClause.append(" AND (entity.address = :address)");
        }
        if (StringUtils.hasText(searchDto.getEmail())) {
            whereClause.append(" AND (entity.email = :email)");
        }
        if (StringUtils.hasText(searchDto.getPhone())) {
            whereClause.append(" AND (entity.phone = :phone)");
        }
        if (StringUtils.hasText(searchDto.getEducation())) {
            whereClause.append(" AND (entity.education = :education)");
        }
        if (StringUtils.hasText(searchDto.getMajor())) {
            whereClause.append(" AND (entity.major = :major)");
        }
        if (!Objects.isNull(searchDto.getRecruit())) {
            whereClause.append(" AND (entity.recruit = :recruit)");
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
        if (Boolean.TRUE.equals(repo.existsCandidateProfileById(id))) {
            repo.deleteById(id);
            return new ResponseData<>(SUCCESS, true);
        } else {
            return new ResponseData<>(ID_NOT_EXIST, false);
        }
    }

    private ErrorMessage validateCandidateProfile(CandidateProfileDto candidateProfileDto, UUID id, String action) {
        if (Constant.Insert.equals(action)) {
            if (Objects.isNull(candidateProfileDto)) return OBJECT_CANNOT_EMPTY;
            if (Boolean.TRUE.equals(repo.existsCandidateProfileByCode(candidateProfileDto.getCode())))
                return CODE_ALREADY_EXIST;
        } else {
            if (Objects.isNull(candidateProfileDto)) return OBJECT_CANNOT_EMPTY;
            if (repo.exclusionCode(candidateProfileDto.getCode(), id) > 0) return CODE_ALREADY_EXIST;

        }
        return SUCCESS;
    }
}
