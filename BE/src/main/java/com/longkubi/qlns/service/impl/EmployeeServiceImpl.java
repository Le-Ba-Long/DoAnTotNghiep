package com.longkubi.qlns.service.impl;

import com.longkubi.qlns.common.Constant;
import com.longkubi.qlns.common.ErrorMessage;
import com.longkubi.qlns.model.dto.ContractDto;
import com.longkubi.qlns.model.dto.EmployeeDto;
import com.longkubi.qlns.model.dto.PositionDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.EmployeeSearchDto;
import com.longkubi.qlns.model.entity.*;
import com.longkubi.qlns.repository.EmployeeRepository;
import com.longkubi.qlns.security.jwt.JwtProvider;
import com.longkubi.qlns.service.*;
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
public class EmployeeServiceImpl implements IEmployeeService {
    @Autowired
    private EmployeeRepository repo;
    @Autowired
    private IPositionService positionService;

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private ICertificateService certificateService;

    @Autowired
    private IContractService contractService;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private EntityManager manager;

    @Override
    public ResponseData<EmployeeDto> create(EmployeeDto employeeDto, String token) {
        ErrorMessage errorMessage = validateEmployee(employeeDto, null, Constant.Insert);
        if (!errorMessage.equals(SUCCESS)) return new ResponseData<>(errorMessage, null);
        Employee entity = new Employee();
        modelMapper.map(employeeDto, entity);
        entity.setCreator(jwtProvider.getUserNameFromToken(token));
        entity.setDateCreated(new Date());
        return new ResponseData<>(modelMapper.map(repo.save(entity), EmployeeDto.class));
    }

    @Override
    public ResponseData<EmployeeDto> update(EmployeeDto employeeDto, UUID id, String token) {
        ErrorMessage errorMessage = validateEmployee(employeeDto, id, Constant.Update);
        if (!errorMessage.equals(SUCCESS)) return new ResponseData<>(errorMessage, null);
        Employee entity = repo.getEmployeeById(id);
        entity.setCode(employeeDto.getCode());
        entity.setFullName(employeeDto.getFullName());
        entity.setImage(employeeDto.getImage());
        entity.setDateOfBirth(employeeDto.getDateOfBirth());
        entity.setSex(employeeDto.getSex());
        entity.setNumberIdentityCard(employeeDto.getNumberIdentityCard());
        entity.setPlaceOfGrantIdentityCard(employeeDto.getPlaceOfGrantIdentityCard());
        entity.setAddress(employeeDto.getAddress());
        entity.setPhone(employeeDto.getPhone());
        entity.setNation(employeeDto.getNation());
        entity.setReligion(employeeDto.getReligion());
        entity.setNumberMedicalInsurance(employeeDto.getNumberMedicalInsurance());
        entity.setPlaceOfIssueMedicalInsurance(employeeDto.getPlaceOfIssueMedicalInsurance());
        entity.setIssuedDateMedicalInsurance(employeeDto.getIssuedDateMedicalInsurance());
        entity.setNumberSocialInsurance(employeeDto.getNumberSocialInsurance());
        entity.setPlaceOfIssueSocialInsurance(employeeDto.getPlaceOfIssueSocialInsurance());
        entity.setIssuedDateSocialInsurance(employeeDto.getIssuedDateSocialInsurance());
        entity.setQuitJobDate(employeeDto.getQuitJobDate());
        entity.setStatus(employeeDto.getStatus());
        entity.setChangedBy(jwtProvider.getUserNameFromToken(token));
        entity.setDateChange(new Date());
        entity.setRefusalReason(employeeDto.getRefusalReason());
        entity.setMajor(employeeDto.getMajor());
        entity.setEmail(employeeDto.getEmail());
        entity.setEducation(employeeDto.getEducation());
        entity.setIssuedDateIdentityCard(employeeDto.getIssuedDateIdentityCard());
        entity.setAdditionalRequestContent(employeeDto.getAdditionalRequestContent());
        entity.setImage(employeeDto.getImage());
        entity.setImageName(employeeDto.getImageName());
        if(!Objects.isNull(employeeDto.getContract())) {
            entity.setContract(modelMapper.map(employeeDto.getContract(), Contract.class));
        }
//        if (!Objects.isNull(employeeDto.getContract())) {
//            ResponseData<ContractDto> contract = contractService.create(employeeDto.getContract(), token);
//            entity.setContract(modelMapper.map(contractService.getContractById(contract.getData().getId()), Contract.class));
//        }
        Set<UUID> listIdPosition = new HashSet<>();
        for (PositionDto position : employeeDto.getPositions()) {
            listIdPosition.add(position.getId());
        }
        //  employeeDto.getPositions().stream().forEach(position -> listIdPosition.add(position.getId()));
        entity.setPositions(positionService.getPositionByListId(listIdPosition).stream()
                .map(position -> modelMapper.map(position, Position.class)).collect(Collectors.toSet()));
        entity.setDepartment(modelMapper.map(employeeDto.getDepartment(), Department.class));
        entity.setCertificate(modelMapper.map(employeeDto.getCertificate(), Certificate.class));
        return new ResponseData<>(modelMapper.map(repo.save(entity), EmployeeDto.class));
    }

    @Override
    public ResponseData<List<EmployeeDto>> getAll() {
       // List<Employee> employeeDtos = repo.findAll();
        List<Employee> employeeDtos = repo.getAll();
        if (employeeDtos.isEmpty()) return new ResponseData<>(SUCCESS, new ArrayList<>());
        return new ResponseData<>(employeeDtos.stream().map(dto -> modelMapper.map(dto, EmployeeDto.class)).collect(Collectors.toList()));
    }

    @Override
    public ResponseData<List<EmployeeDto>> getEmployeesWithoutContract() {
         List<Employee> employeeDtos = repo.getEmployeesWithoutContract();
        if (employeeDtos.isEmpty()) return new ResponseData<>(SUCCESS, new ArrayList<>());
        return new ResponseData<>(employeeDtos.stream().map(dto -> modelMapper.map(dto, EmployeeDto.class)).collect(Collectors.toList()));

    }

    /**
     * Hàm Tìm Kiếm Chức Vụ Theo Điều Kiện Tìm Kiếm Truyền Vào
     *
     * @param searchDto
     * @return ResponseData'<'Page<'DepartmentDto'>>'
     */
    @Override
    public ResponseData<Page<EmployeeDto>> searchByDto(EmployeeSearchDto searchDto) {
        if (Objects.isNull(searchDto)) return new ResponseData<>(OBJECT_CANNOT_EMPTY, null);
        Integer pageIndex = searchDto.getPageIndex();
        Integer pageSize = searchDto.getPageSize();
        if (pageIndex > 0) {
            pageIndex--;
        } else {
            pageIndex = 0;
        }
        StringBuilder sqlCount = new StringBuilder("SELECT COUNT(entity.id) FROM Employee entity WHERE (1=1) ");
        StringBuilder sql = new StringBuilder("SELECT entity FROM Employee entity WHERE (1=1) ");
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
        List<Employee> employeeDtos = querySql.getResultList();
        long count = (long) queryCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        // return new ResponseData<>(new PageImpl<>(departmentList, pageable, count));
        Page<EmployeeDto> result = new PageImpl<>(employeeDtos.stream().map(EmployeeDto::convertFromEntityToDto).collect(Collectors.toList()));
        if (result.isEmpty()) return new ResponseData<>(LIST_IS_EMPTY, null);
        return new ResponseData<>(result);
    }


    private String genOrderByClause(EmployeeSearchDto dto) {
        if (dto.getOrderByFilter() != null && StringUtils.hasText(dto.getOrderByFilter())) {
            switch (dto.getOrderByFilter()) {
                case "code ASC":
                    return "ORDER BY entity.code ASC";
                case "code DESC":
                    return "ORDER BY entity.code DESC";
                case "fullName ASC":
                    return "ORDER BY entity.fullName ASC";
                case "fullName DESC":
                    return "ORDER BY entity.fullName DESC";
                case "dateOfBirth ASC":
                    return "ORDER BY entity.dateOfBirth ASC";
                case "dateOfBirth DESC":
                    return "ORDER BY entity.dateOfBirth DESC";
                case "sex ASC":
                    return "ORDER BY entity.sex ASC";
                case "sex DESC":
                    return "ORDER BY entity.sex DESC";
                case "numberIdentityCard ASC":
                    return "ORDER BY entity.numberIdentityCard ASC";
                case "numberIdentityCard DESC":
                    return "ORDER BY entity.numberIdentityCard DESC";
                case "placeOfGrantIdentityCard ASC":
                    return "ORDER BY entity.placeOfGrantIdentityCard ASC";
                case "placeOfGrantIdentityCard DESC":
                    return "ORDER BY entity.placeOfGrantIdentityCard DESC";
                case "DateChange ASC":
                    return "ORDER BY entity.dateChange ASC";
                case "DateChange DESC":
                    return "ORDER BY entity.dateChange DESC";
            }
        }
        return "";
    }

    private void setParameter(Query query, EmployeeSearchDto searchDto) {
        if (!Objects.isNull(searchDto.getId())) {
            query.setParameter("id", searchDto.getId());
        }
        if (StringUtils.hasText(searchDto.getCode())) {
            query.setParameter("code", searchDto.getCode());
        }
        if (StringUtils.hasText(searchDto.getFullName())) {
            query.setParameter("fullName", '%' + searchDto.getFullName() + '%');
        }
        if (StringUtils.hasText(searchDto.getImage())) {
            query.setParameter("image", searchDto.getImage());
        }
        if (Objects.isNull(searchDto.getDateOfBirth())) {
            Date dateOfBirth = new Date();
            dateOfBirth.setHours(0);
            dateOfBirth.setMinutes(0);
            dateOfBirth.setSeconds(0);
            dateOfBirth.setDate(searchDto.getDateOfBirth().getDate());
            dateOfBirth.setMonth(searchDto.getDateOfBirth().getMonth());
            dateOfBirth.setYear(searchDto.getDateOfBirth().getYear());
            query.setParameter("dateOfBirth", dateOfBirth);
        }
        if (StringUtils.hasText(searchDto.getSex())) {
            query.setParameter("sex", searchDto.getSex());
        }
        if (!Objects.isNull(searchDto.getNumberIdentityCard())) {
            query.setParameter("numberIdentityCard", searchDto.getNumberIdentityCard());
        }
        if (StringUtils.hasText(searchDto.getPlaceOfGrantIdentityCard())) {
            query.setParameter("placeOfGrantIdentityCard", searchDto.getPlaceOfGrantIdentityCard());
        }
        if (StringUtils.hasText(searchDto.getAddress())) {
            query.setParameter("address", searchDto.getAddress());
        }
        if (StringUtils.hasText(searchDto.getPhone())) {
            query.setParameter("phone", searchDto.getPhone());
        }
        if (StringUtils.hasText(searchDto.getNation())) {
            query.setParameter("nation", searchDto.getNation());
        }
        if (StringUtils.hasText(searchDto.getReligion())) {
            query.setParameter("religion", searchDto.getReligion());
        }
        if (!Objects.isNull(searchDto.getNumberMedicalInsurance())) {
            query.setParameter("numberMedicalInsurance", searchDto.getNumberMedicalInsurance());
        }
        if (!Objects.isNull(searchDto.getPlaceOfIssueMedicalInsurance())) {
            query.setParameter("placeOfIssueMedicalInsurance", searchDto.getPlaceOfIssueMedicalInsurance());
        }
        if (!Objects.isNull(searchDto.getIssuedDateMedicalInsurance())) {
            Date issuedDateMedicalInsurance = new Date();
            issuedDateMedicalInsurance.setHours(0);
            issuedDateMedicalInsurance.setMinutes(0);
            issuedDateMedicalInsurance.setSeconds(0);
            issuedDateMedicalInsurance.setDate(searchDto.getIssuedDateMedicalInsurance().getDate());
            issuedDateMedicalInsurance.setMonth(searchDto.getIssuedDateMedicalInsurance().getMonth());
            issuedDateMedicalInsurance.setYear(searchDto.getIssuedDateMedicalInsurance().getYear());
            query.setParameter("issuedDateMedicalInsurance", issuedDateMedicalInsurance);
        }
        if (!Objects.isNull(searchDto.getNumberSocialInsurance())) {
            query.setParameter("numberSocialInsurance", searchDto.getNumberSocialInsurance());
        }
        if (StringUtils.hasText(searchDto.getPlaceOfIssueSocialInsurance())) {
            query.setParameter("placeOfIssueSocialInsurance", searchDto.getPlaceOfIssueSocialInsurance());
        }
        if (!Objects.isNull(searchDto.getIssuedDateSocialInsurance())) {
            Date issuedDateSocialInsurance = new Date();
            issuedDateSocialInsurance.setHours(0);
            issuedDateSocialInsurance.setMinutes(0);
            issuedDateSocialInsurance.setSeconds(0);
            issuedDateSocialInsurance.setDate(searchDto.getIssuedDateSocialInsurance().getDate());
            issuedDateSocialInsurance.setMonth(searchDto.getIssuedDateSocialInsurance().getMonth());
            issuedDateSocialInsurance.setYear(searchDto.getIssuedDateSocialInsurance().getYear());
            query.setParameter("issuedDateSocialInsurance", issuedDateSocialInsurance);
        }

        if (!Objects.isNull(searchDto.getQuitJobDate())) {
            Date quitJobDate = new Date();
            quitJobDate.setHours(0);
            quitJobDate.setMinutes(0);
            quitJobDate.setSeconds(0);
            quitJobDate.setDate(searchDto.getQuitJobDate().getDate());
            quitJobDate.setMonth(searchDto.getQuitJobDate().getMonth());
            quitJobDate.setYear(searchDto.getQuitJobDate().getYear());
            query.setParameter("quitJobDate", quitJobDate);
        }
        if (!Objects.isNull(searchDto.getStatus())) {
            query.setParameter("status", searchDto.getStatus());
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

    private String genWhereClause(EmployeeSearchDto searchDto) {
        StringBuilder whereClause = new StringBuilder();
        if (!Objects.isNull(searchDto.getId())) {
            whereClause.append(" AND (entity.id = :id)");
        }
        if (StringUtils.hasText(searchDto.getCode())) {
            whereClause.append(" AND (entity.code LIKE :code)");
        }
        if (StringUtils.hasText(searchDto.getFullName())) {
            whereClause.append(" AND (entity.fullName LIKE :fullName)");
        }
        if (StringUtils.hasText(searchDto.getImage())) {
            whereClause.append(" AND (entity.image = :image)");
        }
        if (!Objects.isNull(searchDto.getDateOfBirth())) {
            whereClause.append(" AND (entity.dateOfBirth  = :dateOfBirth)");
        }
        if (StringUtils.hasText(searchDto.getSex())) {
            whereClause.append(" AND (entity.sex = :sex)");
        }
        if (Objects.isNull(searchDto.getNumberIdentityCard())) {
            whereClause.append(" AND (entity.numberIdentityCard  =:numberIdentityCard)");
        }
        if (StringUtils.hasText(searchDto.getPlaceOfGrantIdentityCard())) {
            whereClause.append(" AND (entity.placeOfGrantIdentityCard = :placeOfGrantIdentityCard)");
        }
        if (StringUtils.hasText(searchDto.getAddress())) {
            whereClause.append(" AND (entity.address = :address)");
        }
        if (StringUtils.hasText(searchDto.getPhone())) {
            whereClause.append(" AND (entity.phone = :phone)");
        }

        if (StringUtils.hasText(searchDto.getNation())) {
            whereClause.append(" AND (entity.nation = :nation)");
        }
        if (StringUtils.hasText(searchDto.getReligion())) {
            whereClause.append(" AND (entity.religion = :religion)");
        }
        if (!Objects.isNull(searchDto.getNumberMedicalInsurance())) {
            whereClause.append(" AND (entity.numberMedicalInsurance = :numberMedicalInsurance)");
        }
        if (StringUtils.hasText(searchDto.getPlaceOfIssueMedicalInsurance())) {
            whereClause.append(" AND (entity.placeOfIssueMedicalInsurance = :placeOfIssueMedicalInsurance)");
        }
        if (!Objects.isNull(searchDto.getIssuedDateMedicalInsurance())) {
            whereClause.append(" AND (entity.issuedDateMedicalInsurance = :issuedDateMedicalInsurance)");
        }
        if (!Objects.isNull(searchDto.getNumberSocialInsurance())) {
            whereClause.append(" AND (entity.numberSocialInsurance = :numberSocialInsurance)");
        }
        if (StringUtils.hasText(searchDto.getPlaceOfIssueSocialInsurance())) {
            whereClause.append(" AND (entity.placeOfIssueSocialInsurance = :placeOfIssueSocialInsurance)");
        }
        if (!Objects.isNull(searchDto.getIssuedDateSocialInsurance())) {
            whereClause.append(" AND (entity.issuedDateSocialInsurance = :issuedDateSocialInsurance)");
        }
        if (!Objects.isNull(searchDto.getQuitJobDate())) {
            whereClause.append(" AND (entity.quitJobDate = :quitJobDate)");
        }

        if (!Objects.isNull(searchDto.getStatus())) {
            whereClause.append(" AND (entity.status = :status)");
        }
        if (StringUtils.hasText(searchDto.getCreator())) {
            whereClause.append(" AND (entity.creator = :creator)");
        }
        if (StringUtils.hasText(searchDto.getPlaceOfIssueSocialInsurance())) {
            whereClause.append(" AND (entity.placeOfIssueSocialInsurance = :placeOfIssueSocialInsurance)");
        }

        if (!Objects.isNull(searchDto.getDateCreated())) {
            whereClause.append(" AND (entity.dateCreated >= :dateCreated)");
        }
        if (StringUtils.hasText(searchDto.getChangedBy())) {
            whereClause.append(" AND (entity.changedBy = :changedBy)");
        }
        if (!Objects.isNull(searchDto.getDateChange())) {
            whereClause.append(" AND (entity.dateChange >= :dateChange)");
        }
        return whereClause.toString();
    }

    @Override
    public ResponseData<Boolean> deleteById(UUID id) {
        if (Boolean.TRUE.equals(repo.existsEmployeeById(id))) {
            repo.deleteById(id);
            return new ResponseData<>(SUCCESS, true);
        } else {
            return new ResponseData<>(ID_NOT_EXIST, false);
        }
    }

    private ErrorMessage validateEmployee(EmployeeDto certificateDto, UUID id, String action) {
        if (Constant.Insert.equals(action)) {
            if (Objects.isNull(certificateDto)) return OBJECT_CANNOT_EMPTY;
            if (Boolean.TRUE.equals(repo.existsEmployeeByCode(certificateDto.getCode()))) return CODE_ALREADY_EXIST;
            // if (Boolean.TRUE.equals(repo.existsCertificateByName(certificateDto.getName()))) return NAME_EXIST;
        } else {
            if (Objects.isNull(certificateDto)) return OBJECT_CANNOT_EMPTY;
            if (repo.exclusionCode(certificateDto.getCode(), id) > 0) return CODE_ALREADY_EXIST;
            //  if (repo.exclusionName(certificateDto.getName(), id) > 0) return NAME_EXIST;
        }
        return SUCCESS;
    }
}
