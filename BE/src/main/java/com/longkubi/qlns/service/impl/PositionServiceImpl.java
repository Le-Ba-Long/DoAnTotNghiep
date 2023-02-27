package com.longkubi.qlns.service.impl;

import com.longkubi.qlns.common.Constant;
import com.longkubi.qlns.common.ErrorMessage;
import com.longkubi.qlns.model.dto.PositionDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.PositionSearchDto;
import com.longkubi.qlns.model.entity.Position;
import com.longkubi.qlns.repository.PositionRepository;
import com.longkubi.qlns.security.jwt.JwtProvider;
import com.longkubi.qlns.service.IPositionService;
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
public class PositionServiceImpl implements IPositionService {
    @Autowired
    private PositionRepository repo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private EntityManager manager;
    @Override
    public ResponseData<PositionDto> create(PositionDto positionDto, String token) {
        ErrorMessage errorMessage = validateLanguage(positionDto, null, Constant.Insert);
        if (!errorMessage.equals(SUCCESS)) return new ResponseData<>(errorMessage, null);
        Position entity = new Position();
        modelMapper.map(positionDto, entity);
        entity.setCreator(jwtProvider.getUserNameFromToken(token));
        entity.setDateCreated(new Date());
        return new ResponseData<>(modelMapper.map(repo.save(entity), PositionDto.class));
    }
    @Override
    public ResponseData<PositionDto> update(PositionDto positionDto, UUID id, String token) {
        ErrorMessage errorMessage = validateLanguage(positionDto, id, Constant.Update);
        if (!errorMessage.equals(SUCCESS)) return new ResponseData<>(errorMessage, null);
        Position entity = repo.getPositionById(id);
        entity.setCode(positionDto.getCode());
        entity.setName(positionDto.getName());
        entity.setDescription(positionDto.getDescription());
        entity.setChangedBy(jwtProvider.getUserNameFromToken(token));
        entity.setDateChange(new Date());
        return new ResponseData<>(modelMapper.map(repo.save(entity), PositionDto.class));
    }
    @Override
    public ResponseData<List<PositionDto>> getAll() {
        // List<Position> positions = repo.findAll();
        List<Position> positions = repo.getAll();
        if (positions.isEmpty()) return new ResponseData<>(SUCCESS, null);
        return new ResponseData<>(positions.stream().map(dto -> modelMapper.map(dto, PositionDto.class)).collect(Collectors.toList()));
    }

    /**
     * Hàm Tìm Kiếm Chức Vụ Theo Điều Kiện Tìm Kiếm Truyền Vào
     *
     * @param searchDto
     * @return ResponseData'<'Page<'DepartmentDto'>>'
     */
    @Override
    public ResponseData<Page<PositionDto>> searchByDto(PositionSearchDto searchDto) {
        if (Objects.isNull(searchDto)) return new ResponseData<>(OBJECT_CANNOT_EMPTY, null);
        Integer pageIndex = searchDto.getPageIndex();
        Integer pageSize = searchDto.getPageSize();
        if (pageIndex > 0) {
            pageIndex--;
        } else {
            pageIndex = 0;
        }
        StringBuilder sqlCount = new StringBuilder("SELECT COUNT(entity.id) FROM Position entity WHERE (1=1) ");
        StringBuilder sql = new StringBuilder("SELECT entity FROM Position entity WHERE (1=1) ");
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
        List<Position> positionDtoList = querySql.getResultList();
        long count = (long) queryCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        // return new ResponseData<>(new PageImpl<>(departmentList, pageable, count));
        Page<PositionDto> result = new PageImpl<>(positionDtoList.stream().map(PositionDto::convertFromEntityToDto).collect(Collectors.toList()));
        if (result.isEmpty()) return new ResponseData<>(LIST_IS_EMPTY, null);
        return new ResponseData<>(result);
    }

    private String genOrderByClause(PositionSearchDto dto) {
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

    private void setParameter(Query query, PositionSearchDto searchDto) {
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

    private String genWhereClause(PositionSearchDto searchDto) {
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
        if (Boolean.TRUE.equals(repo.existsPositionById(id))) {
            repo.deleteById(id);
            return new ResponseData<>(SUCCESS, true);
        } else {
            return new ResponseData<>(ID_NOT_EXIST, false);
        }
    }
    @Override
    public PositionDto getPositionById(UUID id) {
        if (repo.existsPositionById(id)) {
            return modelMapper.map(repo.getPositionById(id), PositionDto.class);
        } else {
            return null;
        }
    }
    @Override
    public Set<PositionDto> getPositionByListId(Set<UUID> ids) {
        return repo.getPositionByListId(ids).stream()
                .map(position -> modelMapper.map(position, PositionDto.class))
                .collect(Collectors.toSet());
    }
    private ErrorMessage validateLanguage(PositionDto positionDto, UUID id, String action) {
        if (Constant.Insert.equals(action)) {
            if (Objects.isNull(positionDto)) return OBJECT_CANNOT_EMPTY;
            if (Boolean.TRUE.equals(repo.existsPositionByCode(positionDto.getCode()))) return CODE_ALREADY_EXIST;
            if (Boolean.TRUE.equals(repo.existsPositionByName(positionDto.getName()))) return NAME_EXIST;
        } else {
            if (Objects.isNull(positionDto)) return OBJECT_CANNOT_EMPTY;
            if (repo.exclusionCode(positionDto.getCode(), id) > 0) return CODE_ALREADY_EXIST;
            if (repo.exclusionName(positionDto.getName(), id) > 0) return NAME_EXIST;
        }
        return SUCCESS;
    }
}
