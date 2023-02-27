package com.longkubi.qlns.service.impl;

import com.longkubi.qlns.common.Constant;
import com.longkubi.qlns.common.ErrorMessage;
import com.longkubi.qlns.model.dto.DepartmentDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.DepartmentSearchDto;
import com.longkubi.qlns.model.entity.Department;
import com.longkubi.qlns.repository.DepartmentRepository;
import com.longkubi.qlns.security.jwt.JwtProvider;
import com.longkubi.qlns.service.IDepartmentService;
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
public class DepartmentServiceImpl implements IDepartmentService {
    @Autowired
    private DepartmentRepository repo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private EntityManager manager;

    @Override
    public ResponseData<DepartmentDto> create(DepartmentDto departmentDto, String token) {
        ErrorMessage errorMessage = validateLanguage(departmentDto, null, Constant.Insert);
        if (!errorMessage.equals(SUCCESS)) return new ResponseData<>(errorMessage, null);
        Department entity = new Department();
        modelMapper.map(departmentDto, entity);
        entity.setCreator(jwtProvider.getUserNameFromToken(token));
        entity.setDateCreated(new Date());
        return new ResponseData<>(modelMapper.map(repo.save(entity), DepartmentDto.class));
    }
    @Override
    public ResponseData<DepartmentDto> update(DepartmentDto departmentDto, UUID id, String token) {
        ErrorMessage errorMessage = validateLanguage(departmentDto, id, Constant.Update);
        if (!errorMessage.equals(SUCCESS)) return new ResponseData<>(errorMessage, null);
        Department entity = repo.getDepartmentById(id);
        entity.setCode(departmentDto.getCode());
        entity.setName(departmentDto.getName());
        entity.setDescription(departmentDto.getDescription());
        entity.setChangedBy(jwtProvider.getUserNameFromToken(token));
        entity.setDateChange(new Date());
        return new ResponseData<>(modelMapper.map(repo.save(entity), DepartmentDto.class));
    }
    @Override
    public ResponseData<List<DepartmentDto>> getAll() {
        //  List<Department> departmentDtos = repo.findAll();
        List<Department> departmentDtos = repo.getAll();
        if (departmentDtos.isEmpty()) return new ResponseData<>(SUCCESS, new ArrayList<>());
        return new ResponseData<>(departmentDtos.stream().map(dto -> modelMapper.map(dto, DepartmentDto.class)).collect(Collectors.toList()));
    }

    /**
     * Hàm Tìm Kiếm Phòng Ban Theo Điều Kiện Tìm Kiếm Truyền Vào
     *
     * @param searchDto
     * @return ResponseData'<'Page<'DepartmentDto'>>'
     */
    @Override
    public ResponseData<Page<DepartmentDto>> searchByDto(DepartmentSearchDto searchDto) {
        if (Objects.isNull(searchDto)) return new ResponseData<>(OBJECT_CANNOT_EMPTY, null);
        Integer pageIndex = searchDto.getPageIndex();
        Integer pageSize = searchDto.getPageSize();
        if (pageIndex > 0) {
            pageIndex--;
        } else {
            pageIndex = 0;
        }
        StringBuilder sqlCount = new StringBuilder("SELECT COUNT(entity.id) FROM Department entity WHERE (1=1) ");
        StringBuilder sql = new StringBuilder("SELECT entity FROM Department entity WHERE (1=1) ");
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
        List<Department> departmentList = querySql.getResultList();
        long count = (long) queryCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        // return new ResponseData<>(new PageImpl<>(departmentList, pageable, count));
        Page<DepartmentDto> result = new PageImpl<>(departmentList.stream().map(DepartmentDto::convertFromEntityToDto).collect(Collectors.toList()));
        if (result.isEmpty()) return new ResponseData<>(LIST_IS_EMPTY, null);
        return new ResponseData<>(result);
    }


    private String genOrderByClause(DepartmentSearchDto dto) {
        if (dto.getOrderByFilter() != null && !StringUtils.hasText(dto.getOrderByFilter())) {
            switch (dto.getOrderByFilter()) {
                case "Code ASC":
                    return "ORDER BY entity.code ASC";
                case "Code DESC":
                    return "ORDER BY entity.code DESC";
                case "Name ASC":
                    return "ORDER BY entity.name ASC";
                case "Name DESC":
                    return "ORDER BY entity.name DESC";
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

    private void setParameter(Query query, DepartmentSearchDto searchDto) {
        if (!Objects.isNull(searchDto.getId())) {
            query.setParameter("id", searchDto.getId());
        }
        if (StringUtils.hasText(searchDto.getCode())) {
            query.setParameter("code", searchDto.getCode());
        }
        if (StringUtils.hasText(searchDto.getName())) {
            query.setParameter("name", '%' + searchDto.getName() + '%');
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

    private String genWhereClause(DepartmentSearchDto searchDto) {
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
    public ResponseData<Boolean> deleteById(UUID id, String token) {
        if (id != null) {
            if (Boolean.TRUE.equals(repo.existsDepartmentById(id))) {
                repo.deleteById(id);
                return new ResponseData<>(SUCCESS, true);
            } else {
                return new ResponseData<>(ID_NOT_EXIST, false);
            }
        } else {
            return new ResponseData<>(DATA_WRONG_FORM, null);
        }
    }

    private ErrorMessage validateLanguage(DepartmentDto departmentDto, UUID id, String action) {
        if (Constant.Insert.equals(action)) {
            if (Objects.isNull(departmentDto)) return OBJECT_CANNOT_EMPTY;
            if (Boolean.TRUE.equals(repo.existsDepartmentByCode(departmentDto.getCode()))) return CODE_ALREADY_EXIST;
            if (Boolean.TRUE.equals(repo.existsDepartmentByName(departmentDto.getName()))) return NAME_EXIST;
        } else {
            if (Objects.isNull(departmentDto)) return OBJECT_CANNOT_EMPTY;
            if (repo.exclusionCode(departmentDto.getCode(), id) > 0) return CODE_ALREADY_EXIST;
            if (repo.exclusionName(departmentDto.getName(), id) > 0) return NAME_EXIST;
        }
        return SUCCESS;
    }
}
