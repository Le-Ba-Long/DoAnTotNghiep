package com.longkubi.qlns.service.impl;

import com.longkubi.qlns.common.Constant;
import com.longkubi.qlns.common.ErrorMessage;
import com.longkubi.qlns.model.dto.CertificateDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.CertificateSearchDto;
import com.longkubi.qlns.model.entity.Certificate;
import com.longkubi.qlns.repository.CertificateRepository;
import com.longkubi.qlns.security.jwt.JwtProvider;
import com.longkubi.qlns.service.ICertificateService;
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
public class CertificateServiceImpl implements ICertificateService {
    @Autowired
    private CertificateRepository repo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private EntityManager manager;

    @Override
    public ResponseData<CertificateDto> create(CertificateDto certificateDto, String token) {
        ErrorMessage errorMessage = validateLanguage(certificateDto, null, Constant.Insert);
        if (!errorMessage.equals(SUCCESS)) return new ResponseData<>(errorMessage, null);
        Certificate entity = new Certificate();
        modelMapper.map(certificateDto, entity);
        entity.setCreator(jwtProvider.getUserNameFromToken(token));
        entity.setDateCreated(new Date());
        return new ResponseData<>(modelMapper.map(repo.save(entity), CertificateDto.class));

    }


    @Override
    public ResponseData<CertificateDto> update(CertificateDto certificateDto, UUID id, String token) {
        ErrorMessage errorMessage = validateLanguage(certificateDto, id, Constant.Update);
        if (!errorMessage.equals(SUCCESS)) return new ResponseData<>(errorMessage, null);
        Certificate entity = repo.getCertificateById(id);
        entity.setCode(certificateDto.getCode());
        entity.setName(certificateDto.getName());
        entity.setMajors(certificateDto.getMajors());
        entity.setIssuedDate(certificateDto.getIssuedDate());
        entity.setGrantedBy(certificateDto.getGrantedBy());
        entity.setChangedBy(jwtProvider.getUserNameFromToken(token));
        entity.setDateChange(new Date());
        return new ResponseData<>(modelMapper.map(repo.save(entity), CertificateDto.class));
    }

    @Override
    public ResponseData<List<CertificateDto>> getAll() {
      //  List<Certificate> certificates = repo.findAll();
        List<Certificate> certificates = repo.getAll();
        if (certificates.isEmpty()) return new ResponseData<>(SUCCESS, new ArrayList<>());
        return new ResponseData<>(certificates.stream().map(dto -> modelMapper.map(dto, CertificateDto.class)).collect(Collectors.toList()));
    }

    /**
     * Hàm Tìm Kiếm Chức Vụ Theo Điều Kiện Tìm Kiếm Truyền Vào
     *
     * @param searchDto
     * @return ResponseData'<'Page<'DepartmentDto'>>'
     */
    @Override
    public ResponseData<Page<CertificateDto>> searchByDto(CertificateSearchDto searchDto) {
        if (Objects.isNull(searchDto)) return new ResponseData<>(OBJECT_CANNOT_EMPTY, null);
        Integer pageIndex = searchDto.getPageIndex();
        Integer pageSize = searchDto.getPageSize();
        if (pageIndex > 0) {
            pageIndex--;
        } else {
            pageIndex = 0;
        }
        StringBuilder sqlCount = new StringBuilder("SELECT COUNT(entity.id) FROM Certificate entity WHERE (1=1) ");
        StringBuilder sql = new StringBuilder("SELECT entity FROM Certificate entity WHERE (1=1) ");
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
        List<Certificate> certificateDtos = querySql.getResultList();
        long count = (long) queryCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        // return new ResponseData<>(new PageImpl<>(departmentList, pageable, count));
        Page<CertificateDto> result = new PageImpl<>(certificateDtos.stream().map(CertificateDto::convertFromEntityToDto).collect(Collectors.toList()));
        if (result.isEmpty()) return new ResponseData<>(LIST_IS_EMPTY, null);
        return new ResponseData<>(result);
    }


    private String genOrderByClause(CertificateSearchDto dto) {
        if (dto.getOrderByFilter() != null && StringUtils.hasText(dto.getOrderByFilter())) {
            switch (dto.getOrderByFilter()) {
                case "Code ASC":
                    return "ORDER BY entity.code ASC";
                case "Code DESC":
                    return "ORDER BY entity.code DESC";
                case "Name ASC":
                    return "ORDER BY entity.name ASC";
                case "Name DESC":
                    return "ORDER BY entity.name DESC";
                case "Majors ASC":
                    return "ORDER BY entity.majors ASC";
                case "Majors DESC":
                    return "ORDER BY entity.majors DESC";
                case "GrantedBy ASC":
                    return "ORDER BY entity.grantedBy ASC";
                case "GrantedBy DESC":
                    return "ORDER BY entity.grantedBy DESC";
                case "IssuedDate ASC":
                    return "ORDER BY entity.issuedDate ASC";
                case "IssuedDate DESC":
                    return "ORDER BY entity.issuedDate DESC";
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

    private void setParameter(Query query, CertificateSearchDto searchDto) {
        if (!Objects.isNull(searchDto.getId())) {
            query.setParameter("id", searchDto.getId());
        }
        if (StringUtils.hasText(searchDto.getCode())) {
            query.setParameter("code", searchDto.getCode());
        }
        if (StringUtils.hasText(searchDto.getName())) {
            query.setParameter("name", '%' + searchDto.getName() + '%');
        }
        if (StringUtils.hasText(searchDto.getMajors())) {
            query.setParameter("majors", '%' + searchDto.getMajors() + '%');
        }
        if (Objects.isNull(searchDto.getIssuedDate())) {
            Date issuedDate = new Date();
            issuedDate.setHours(0);
            issuedDate.setMinutes(0);
            issuedDate.setSeconds(0);
            issuedDate.setDate(searchDto.getIssuedDate().getDate());
            issuedDate.setMonth(searchDto.getIssuedDate().getMonth());
            issuedDate.setYear(searchDto.getIssuedDate().getYear());
            query.setParameter("issuedDate", issuedDate);
        }
        if (StringUtils.hasText(searchDto.getGrantedBy())) {
            query.setParameter("grantedBy", '%' + searchDto.getGrantedBy() + '%');
        }
        if (StringUtils.hasText(searchDto.getCreator())) {
            query.setParameter("creator", searchDto.getCreator());
        }
        if (StringUtils.hasText(searchDto.getChangedBy())) {
            query.setParameter("changeBy", searchDto.getChangedBy());
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

    private String genWhereClause(CertificateSearchDto searchDto) {
        StringBuilder whereClause = new StringBuilder();
        if (!Objects.isNull(searchDto.getId())) {
            whereClause.append(" AND (entity.id = :id)");
        }
        if (StringUtils.hasText(searchDto.getCode())) {
            whereClause.append(" AND (entity.code LIKE :code)");
        }
        if (StringUtils.hasText(searchDto.getName())) {
            whereClause.append(" AND (entity.name LIKE :name)");
        }
        if (StringUtils.hasText(searchDto.getMajors())) {
            whereClause.append(" AND (entity.majors LIKE :majors)");
        }
        if (!Objects.isNull(searchDto.getIssuedDate())) {
            whereClause.append(" AND (entity.issuedDate LIKE :issuedDate)");
        }
        if (StringUtils.hasText(searchDto.getGrantedBy())) {
            whereClause.append(" AND (entity.grantedBy LIKE :grantedBy)");
        }
        if (StringUtils.hasText(searchDto.getCreator())) {
            whereClause.append(" AND (entity.creator LIKE :creator)");
        }
        if (StringUtils.hasText(searchDto.getChangedBy())) {
            whereClause.append(" AND (entity.changeBy LIKE :changeBy)");
        }
        if (!Objects.isNull(searchDto.getDateCreated())) {
            whereClause.append(" AND (entity.dateCreated >= :dateCreated)");
        }
        if (!Objects.isNull(searchDto.getDateChange())) {
            whereClause.append(" AND (entity.dateChange >= :dateChange)");
        }
        return whereClause.toString();
    }

    @Override
    public ResponseData<Boolean> deleteById(UUID id) {
        if (Boolean.TRUE.equals(repo.existsCertificateById(id))) {
            repo.deleteById(id);
            return new ResponseData<>(SUCCESS,true);
        } else {
            return new ResponseData<>(ID_NOT_EXIST, false);
        }
    }

    private ErrorMessage validateLanguage(CertificateDto certificateDto, UUID id, String action) {
        if (Constant.Insert.equals(action)) {
            if (Objects.isNull(certificateDto)) return OBJECT_CANNOT_EMPTY;
            if (Boolean.TRUE.equals(repo.existsCertificateByCode(certificateDto.getCode()))) return CODE_ALREADY_EXIST;
           // if (Boolean.TRUE.equals(repo.existsCertificateByName(certificateDto.getName()))) return NAME_EXIST;
        } else {
            if (Objects.isNull(certificateDto)) return OBJECT_CANNOT_EMPTY;
            if (repo.exclusionCode(certificateDto.getCode(), id) > 0) return CODE_ALREADY_EXIST;
          //  if (repo.exclusionName(certificateDto.getName(), id) > 0) return NAME_EXIST;
        }
        return SUCCESS;
    }
}
