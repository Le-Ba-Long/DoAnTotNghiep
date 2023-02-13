package com.longkubi.qlns.service.impl;

import com.longkubi.qlns.common.Constant;
import com.longkubi.qlns.common.ErrorMessage;
import com.longkubi.qlns.model.dto.ContractDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.ContractSearchDto;
import com.longkubi.qlns.model.entity.Contract;
import com.longkubi.qlns.model.entity.Employee;
import com.longkubi.qlns.repository.ContractRepository;
import com.longkubi.qlns.security.jwt.JwtProvider;
import com.longkubi.qlns.service.IContractService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
public class ContractServiceImpl implements IContractService {
    @Autowired
    private ContractRepository repo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private EntityManager manager;

    @Override
    public ResponseData<ContractDto> create(ContractDto contractDto, String token) {
        ErrorMessage errorMessage = validateContract(contractDto, null, Constant.Insert);
        if (!errorMessage.equals(SUCCESS)) return new ResponseData<>(errorMessage, null);
        Contract entity = new Contract();
        modelMapper.map(contractDto, entity);
        //entity.setEmployee(modelMapper.map(contractDto.getEmployee(), Employee.class));
        entity.setCreator(jwtProvider.getUserNameFromToken(token));
        entity.setDateCreated(new Date());
        return new ResponseData<>(modelMapper.map(repo.save(entity), ContractDto.class));

    }


    @Override
    public ResponseData<ContractDto> update(ContractDto contractDto, UUID id, String token) {
        ErrorMessage errorMessage = validateContract(contractDto, id, Constant.Update);
        if (!errorMessage.equals(SUCCESS)) return new ResponseData<>(errorMessage, null);
        Contract entity = repo.getContractById(id);
        entity.setCode(contractDto.getCode());
        //entity.setEmployee(contractDto.getEmployee());
        entity.setSigningDate(contractDto.getSigningDate());
        entity.setContractEffect(contractDto.getContractEffect());
        entity.setStatus(contractDto.getStatus());
        entity.setBasicSalary(contractDto.getBasicSalary());
        entity.setNameLeader(contractDto.getNameLeader());
        entity.setPostionLeader(contractDto.getPostionLeader());
        entity.setHourlyRate(contractDto.getHourlyRate());
        entity.setCoefficientSalary(contractDto.getCoefficientSalary());
        entity.setChangedBy(jwtProvider.getUserNameFromToken(token));
        entity.setDateChange(new Date());
        return new ResponseData<>(modelMapper.map(repo.save(entity), ContractDto.class));
    }

    @Override
    public ResponseData<List<ContractDto>> getAll() {
       // List<Contract> contractList = repo.findAll();
        List<Contract> contractList = repo.getAll();
        if (contractList.isEmpty()) return new ResponseData<>(SUCCESS, new ArrayList<>());
        return new ResponseData<>(contractList.stream().map(dto -> modelMapper.map(dto, ContractDto.class)).collect(Collectors.toList()));
    }

    /**
     * Hàm Tìm Kiếm Chức Vụ Theo Điều Kiện Tìm Kiếm Truyền Vào
     *
     * @param searchDto
     * @return ResponseData'<'Page<'DepartmentDto'>>'
     */
    @Override
    public ResponseData<Page<ContractDto>> searchByDto(ContractSearchDto searchDto) {
        if (Objects.isNull(searchDto)) return new ResponseData<>(OBJECT_CANNOT_EMPTY, null);
        Integer pageIndex = searchDto.getPageIndex();
        Integer pageSize = searchDto.getPageSize();
        if (pageIndex > 0) {
            pageIndex--;
        } else {
            pageIndex = 0;
        }
        StringBuilder sqlCount = new StringBuilder("SELECT COUNT(entity.id) FROM Contract entity WHERE (1=1) ");
        StringBuilder sql = new StringBuilder("SELECT entity FROM Contract entity WHERE (1=1) ");
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
        List<Contract> contractList = querySql.getResultList();
        long count = (long) queryCount.getSingleResult();
        Page<ContractDto> result = new PageImpl<>(contractList.stream().map(ContractDto::convertFromEntityToDto).collect(Collectors.toList()));
        if (result.isEmpty()) return new ResponseData<>(LIST_IS_EMPTY, null);
        return new ResponseData<>(result);
    }


    private String genOrderByClause(ContractSearchDto dto) {
        if (dto.getOrderByFilter() != null && StringUtils.hasText(dto.getOrderByFilter())) {
            switch (dto.getOrderByFilter()) {
                case "Code ASC":
                    return "ORDER BY entity.code ASC";
                case "Code DESC":
                    return "ORDER BY entity.code DESC";
                case "SigningDate ASC":
                    return "ORDER BY entity.signingDate ASC";
                case "SigningDate DESC":
                    return "ORDER BY entity.signingDate DESC";
                case "ContractEffect ASC":
                    return "ORDER BY entity.contractEffect ASC";
                case "ContractEffect DESC":
                    return "ORDER BY entity.contractEffect DESC";
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

    private void setParameter(Query query, ContractSearchDto searchDto) {
        if (!Objects.isNull(searchDto.getId())) {
            query.setParameter("id", searchDto.getId());
        }
        if (StringUtils.hasText(searchDto.getCode())) {
            query.setParameter("code", searchDto.getCode());
        }
        if (!Objects.isNull(searchDto.getEmployee())) {
            query.setParameter("employee", searchDto.getEmployee());
        }
        if (Objects.isNull(searchDto.getSigningDate())) {
            Date signingDate = new Date();
            signingDate.setHours(0);
            signingDate.setMinutes(0);
            signingDate.setSeconds(0);
            signingDate.setDate(searchDto.getSigningDate().getDate());
            signingDate.setMonth(searchDto.getSigningDate().getMonth());
            signingDate.setYear(searchDto.getSigningDate().getYear());
            query.setParameter("signingDate", signingDate);
        }
        if (Objects.isNull(searchDto.getContractEffect())) {
            Date contractEffect = new Date();
            contractEffect.setHours(0);
            contractEffect.setMinutes(0);
            contractEffect.setSeconds(0);
            contractEffect.setDate(searchDto.getContractEffect().getDate());
            contractEffect.setMonth(searchDto.getContractEffect().getMonth());
            contractEffect.setYear(searchDto.getContractEffect().getYear());
            query.setParameter("contractEffect", contractEffect);
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
    }

    private String genWhereClause(ContractSearchDto searchDto) {
        StringBuilder whereClause = new StringBuilder();
        if (!Objects.isNull(searchDto.getId())) {
            whereClause.append(" AND (entity.id = :id)");
        }
        if (StringUtils.hasText(searchDto.getCode())) {
            whereClause.append(" AND (entity.code LIKE :code)");
        }
        if (!Objects.isNull(searchDto.getEmployee())) {
            whereClause.append(" AND (entity.employee = :employee)");
        }
        if (!Objects.isNull(searchDto.getSigningDate())) {
            whereClause.append(" AND (entity.signingDate = :signingDate)");
        }
        if (!Objects.isNull(searchDto.getContractEffect())) {
            whereClause.append(" AND (entity.contractEffect = :contractEffect)");
        }
        if (StringUtils.hasText(searchDto.getStatus())) {
            whereClause.append(" AND (entity.status = :status)");
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
        if (Boolean.TRUE.equals(repo.existsContractById(id))) {
            repo.deleteById(id);
            return new ResponseData<>(SUCCESS,true);
        } else {
            return new ResponseData<>(ID_NOT_EXIST, false);
        }
    }

    @Override
    public ContractDto getContractById(UUID id) {
        return modelMapper.map(repo.getContractById(id),ContractDto.class);
    }

    private ErrorMessage validateContract(ContractDto contractDto, UUID id, String action) {
        if (Constant.Insert.equals(action)) {
            if (Objects.isNull(contractDto)) return OBJECT_CANNOT_EMPTY;
            if (Boolean.TRUE.equals(repo.existsContractByCode(contractDto.getCode()))) return CODE_ALREADY_EXIST;
        } else {
            if (Objects.isNull(contractDto)) return OBJECT_CANNOT_EMPTY;
            if (repo.exclusionCode(contractDto.getCode(), id) > 0) return CODE_ALREADY_EXIST;
            //  if (repo.exclusionName(certificateDto.getName(), id) > 0) return NAME_EXIST;
        }
        return SUCCESS;
    }
}
