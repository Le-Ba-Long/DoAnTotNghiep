package com.longkubi.qlns.service.impl;

import com.longkubi.qlns.common.Constant;
import com.longkubi.qlns.common.ErrorMessage;
import com.longkubi.qlns.model.dto.CommendationAndDisciplineDto;
import com.longkubi.qlns.model.dto.EmployeeDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.CommendationAndDisciplineSearchDto;
import com.longkubi.qlns.model.entity.*;
import com.longkubi.qlns.repository.CommendationAndDisciplineRepository;
import com.longkubi.qlns.repository.ContractRepository;
import com.longkubi.qlns.repository.EmployeeHistoryRepository;
import com.longkubi.qlns.repository.EmployeeRepository;
import com.longkubi.qlns.security.jwt.JwtProvider;
import com.longkubi.qlns.service.ICommendationAndDisciplineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;

import static com.longkubi.qlns.common.Constant.StatusType.*;
import static com.longkubi.qlns.common.ErrorMessage.*;

@Service
@Transactional
public class CommendationAndDisciplineServiceImpl implements ICommendationAndDisciplineService {
    @Autowired
    private CommendationAndDisciplineRepository repo;

    @Autowired
    private ContractRepository contractRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EntityManager manager;
    @Autowired
    private EmployeeHistoryRepository employeeHistoryRepository;

    //    @Override
//    public ResponseData<CommendationAndDisciplineDto> create(CommendationAndDisciplineDto commendationAndDisciplineDto, String token) {
//        ErrorMessage errorMessage = validateContract(commendationAndDisciplineDto, null, Constant.Insert);
//        if (!errorMessage.equals(SUCCESS)) return new ResponseData<>(errorMessage, null);
//        CommendationAndDiscipline entity = new CommendationAndDiscipline();
//        modelMapper.map(commendationAndDisciplineDto, entity);
//     //   entity.setEmployee(modelMapper.map(commendationAndDisciplineDto.getEmployeeDto(),Employee.class));
//        Employee employee = employeeRepository.getEmployeeById(commendationAndDisciplineDto.getEmployeeDto().getId());
//        entity.setEmployee(employee);
//        entity.setCreator(jwtProvider.getUserNameFromToken(token));
//        entity.setDateCreated(new Date());
//        return new ResponseData<>(modelMapper.map(repo.save(entity), CommendationAndDisciplineDto.class));
//
//    }
    @Override
    // @Transactional
    public ResponseData<CommendationAndDisciplineDto> create(CommendationAndDisciplineDto commendationAndDisciplineDto, String token) {
        ErrorMessage errorMessage = validateContract(commendationAndDisciplineDto, null, Constant.Insert);
        if (!errorMessage.equals(SUCCESS)) return new ResponseData<>(errorMessage, null);
        CommendationAndDiscipline entity = new CommendationAndDiscipline();
        modelMapper.map(commendationAndDisciplineDto, entity);
        //entity.setEmployee(modelMapper.map(commendationAndDisciplineDto.getEmployeeDto(),Employee.class ));
//        Employee employee = employeeRepository.getEmployeeById(commendationAndDisciplineDto.getEmployeeDto().getId());
//        entity.setEmployee(employee);
        entity.setCreator(jwtProvider.getUserNameFromToken(token));
        entity.setDateCreated(new Date());
        return new ResponseData<>(modelMapper.map(repo.save(entity), CommendationAndDisciplineDto.class));
    }

    @Override
    public ResponseData<CommendationAndDisciplineDto> update(CommendationAndDisciplineDto commendationAndDisciplineDto, UUID id, String token) {
        ErrorMessage errorMessage = validateContract(commendationAndDisciplineDto, id, Constant.Update);
        if (!errorMessage.equals(SUCCESS)) return new ResponseData<>(errorMessage, null);
        saveEmployeeHistory(commendationAndDisciplineDto);
        CommendationAndDiscipline entity = repo.getCommendationAndDisciplineById(id);
        //entity.setEmployee(contractDto.getEmployee());
        entity.setDay(commendationAndDisciplineDto.getDay());
        entity.setMonth(commendationAndDisciplineDto.getMonth());
        entity.setYear(commendationAndDisciplineDto.getYear());
        entity.setReason(commendationAndDisciplineDto.getReason());
        if (!Objects.isNull(commendationAndDisciplineDto.getIssuedDate())) {
            entity.setIssuedDate(commendationAndDisciplineDto.getIssuedDate());
        }
        // entity.setDecisionDay(commendationAndDisciplineDto.getDecisionDay());
        entity.setType(commendationAndDisciplineDto.getType());
        entity.setDecisionNumber(commendationAndDisciplineDto.getDecisionNumber());
        if (!Objects.isNull(commendationAndDisciplineDto.getRewardDisciplineLevel())) {
            entity.setRewardDisciplineLevel(commendationAndDisciplineDto.getRewardDisciplineLevel());
        }
        if (!Objects.isNull(commendationAndDisciplineDto.getBasicSalary())) {
            entity.setBasicSalary(commendationAndDisciplineDto.getBasicSalary());
        }
        if (!Objects.isNull(commendationAndDisciplineDto.getCoefficientSalary())) {
            entity.setCoefficientSalary(commendationAndDisciplineDto.getCoefficientSalary());
        }
        if (!Objects.isNull(commendationAndDisciplineDto.getHourlyRate())) {
            entity.setHourlyRate(commendationAndDisciplineDto.getHourlyRate());
        }
        entity.setStaffName(commendationAndDisciplineDto.getStaffName());
        entity.setStatus(commendationAndDisciplineDto.getStatus());
        entity.setChangedBy(jwtProvider.getUserNameFromToken(token));
        entity.setDateChange(new Date());
        Employee employee = employeeRepository.getEmployeeById(commendationAndDisciplineDto.getEmployeeDto().getId());
        entity.setEmployee(employee);
        return new ResponseData<>(modelMapper.map(repo.save(entity), CommendationAndDisciplineDto.class));
    }

    @Override
    public ResponseData<List<CommendationAndDisciplineDto>> getAll() {
        // List<Contract> contractList = repo.findAll();
        List<CommendationAndDiscipline> commendationAndDisciplineList = repo.getAll();
        if (commendationAndDisciplineList.isEmpty()) return new ResponseData<>(SUCCESS, new ArrayList<>());
        //   return new ResponseData<>(commendationAndDisciplineList.stream().map(dto -> modelMapper.map(dto, CommendationAndDisciplineDto.class)).collect(Collectors.toList()));
        return new ResponseData<>(commendationAndDisciplineList.stream().map(CommendationAndDisciplineDto::convertFromEntityToDto).collect(Collectors.toList()));
    }

    /**
     * Hàm Tìm Kiếm Chức Vụ Theo Điều Kiện Tìm Kiếm Truyền Vào
     *
     * @param searchDto
     * @return ResponseData'<'Page<'DepartmentDto'>>'
     */
    @Override
    public ResponseData<Page<CommendationAndDisciplineDto>> searchByDto(CommendationAndDisciplineSearchDto searchDto) {
        if (Objects.isNull(searchDto)) return new ResponseData<>(OBJECT_CANNOT_EMPTY, null);
        Integer pageIndex = searchDto.getPageIndex();
        Integer pageSize = searchDto.getPageSize();
        if (pageIndex > 0) {
            pageIndex--;
        } else {
            pageIndex = 0;
        }
        StringBuilder sqlCount = new StringBuilder("SELECT COUNT(entity.id) FROM CommendationAndDiscipline entity WHERE (1=1) ");
        StringBuilder sql = new StringBuilder("SELECT entity FROM CommendationAndDiscipline entity WHERE (1=1) ");
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
        List<CommendationAndDiscipline> contractList = querySql.getResultList();
        long count = (long) queryCount.getSingleResult();
        Page<CommendationAndDisciplineDto> result = new PageImpl<>(contractList.stream().map(entity -> modelMapper.map(entity, CommendationAndDisciplineDto.class)).collect(Collectors.toList()));
        if (result.isEmpty()) return new ResponseData<>(LIST_IS_EMPTY, null);
        return new ResponseData<>(result);
    }


    private String genOrderByClause(CommendationAndDisciplineSearchDto dto) {
        if (dto.getOrderByFilter() != null && StringUtils.hasText(dto.getOrderByFilter())) {
            switch (dto.getOrderByFilter()) {
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

    private void setParameter(Query query, CommendationAndDisciplineSearchDto searchDto) {
        if (!Objects.isNull(searchDto.getId())) {
            query.setParameter("id", searchDto.getId());
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

    private String genWhereClause(CommendationAndDisciplineSearchDto searchDto) {
        StringBuilder whereClause = new StringBuilder();
        if (!Objects.isNull(searchDto.getId())) {
            whereClause.append(" AND (entity.id = :id)");
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
        if (Boolean.TRUE.equals(repo.existsCommendationAndDisciplineById(id))) {
            repo.deleteById(id);
            return new ResponseData<>(SUCCESS, true);
        } else {
            return new ResponseData<>(ID_NOT_EXIST, false);
        }
    }


    private ErrorMessage validateContract(CommendationAndDisciplineDto commendationAndDisciplineDto, UUID id, String action) {
        if (Constant.Insert.equals(action)) {
            if (Objects.isNull(commendationAndDisciplineDto)) return OBJECT_CANNOT_EMPTY;
        } else {
            if (Objects.isNull(commendationAndDisciplineDto)) return OBJECT_CANNOT_EMPTY;
            //  if (repo.exclusionName(certificateDto.getName(), id) > 0) return NAME_EXIST;
        }
        return SUCCESS;
    }

    private void saveEmployeeHistory(CommendationAndDisciplineDto commendationAndDisciplineDto) {
        if (!(Arrays.asList(PENDING.getType(), FIX_REQUEST.getType(), NEW_SAVE.getType(), APPROVED.getType()).contains(commendationAndDisciplineDto.getStatus()))) {
            EmployeeHistory employeeHistory = new EmployeeHistory();
            Employee employee = null;
            String titleRecruit = null;
            EmployeeDto employeeDto = commendationAndDisciplineDto.getEmployeeDto();
            //    ContractDto contract = commendationAndDisciplineDto.getEmployeeDto().getContract();
            Integer basicSalary = commendationAndDisciplineDto.getBasicSalary();
            Float coefficientSalary = commendationAndDisciplineDto.getCoefficientSalary();
            Integer hourlyRate = commendationAndDisciplineDto.getHourlyRate();
            if (commendationAndDisciplineDto.getEmployeeDto().getId() != null) {
                employee = employeeRepository.getEmployeeById(employeeDto.getId());
            }
            assert employee != null;
            titleRecruit = employee.getTitleRecruit();
            String nameDepartment = employeeDto.getDepartment().getName();
            String workingPosition = null;
            byte status = commendationAndDisciplineDto.getStatus();
            byte type = commendationAndDisciplineDto.getType();
            for (Position position : employee.getPositions()) {
                workingPosition = position.getName();
            }
            String s = "- Vị trí làm việc: " + titleRecruit + "\n- Chức vụ làm việc: " + workingPosition + "\n- Phòng ban làm việc: " + nameDepartment + "\n- Mức lương cơ bản: " + basicSalary + "\n- Hệ số lương: " + coefficientSalary + "\n- Số tiền tính cho một giờ làm thêm: " + hourlyRate;
            if (status == FINISHED.getType() && type == 1) {
                employeeHistory.setDate(commendationAndDisciplineDto.getDateChange() == null ? commendationAndDisciplineDto.getDateCreated() : commendationAndDisciplineDto.getDateChange());
                if (StringUtils.hasText(titleRecruit)) {
                    Contract contract = employee.getContract();
                    basicSalary = contract.getBasicSalary();
                    coefficientSalary = contract.getCoefficientSalary();
                    hourlyRate = contract.getHourlyRate();
                    s = "- Vị trí làm việc: " + titleRecruit
                            + "\n- Chức vụ làm việc: " + workingPosition
                            + "\n- Phòng ban làm việc: " + nameDepartment
                            + "\n- Mức lương cơ bản: " + basicSalary
                            + "\n- Hệ số lương: " + coefficientSalary
                            + "\n- Số tiền tính cho một giờ làm thêm: " + hourlyRate
                            + "\n- Số tiền Bị Phạt: " +commendationAndDisciplineDto.getRewardDisciplineLevel() ;
                    employeeHistory.setDescription(s);
                    employeeHistory.setEvent("Nhân Viên Bị Kỉ Luật");
                }
                employeeHistory.setStatus(OFFICIAL_STAFF.getType());
                employeeHistory.setEmployeeHistory(modelMapper.map(employeeDto, Employee.class));
            } else if (status == FINISHED.getType() && type == 2) {
                employeeHistory.setDate(commendationAndDisciplineDto.getDateChange() == null ? commendationAndDisciplineDto.getDateCreated() : commendationAndDisciplineDto.getDateChange());
                if (StringUtils.hasText(titleRecruit)) {
                    employeeHistory.setDescription(s);
                    Contract contract = employee.getContract();
                    basicSalary = contract.getBasicSalary();
                    coefficientSalary = contract.getCoefficientSalary();
                    hourlyRate = contract.getHourlyRate();
                    s = "- Vị trí làm việc: " + titleRecruit
                            + "\n- Chức vụ làm việc: " + workingPosition
                            + "\n- Phòng ban làm việc: " + nameDepartment
                            + "\n- Mức lương cơ bản: " + basicSalary
                            + "\n- Hệ số lương: " + coefficientSalary
                            + "\n- Số tiền tính cho một giờ làm thêm: " + hourlyRate
                            + "\n- Số tiền Được Thưởng: " +commendationAndDisciplineDto.getRewardDisciplineLevel() ;
                    employeeHistory.setEvent("Nhân Viên Được Khen Thưởng");
                }
                employeeHistory.setStatus(OFFICIAL_STAFF.getType());
                employeeHistory.setEmployeeHistory(modelMapper.map(employeeDto, Employee.class));
            } else if (status == FINISHED.getType() && type == 3) {
                employeeHistory.setDate(commendationAndDisciplineDto.getDateChange() == null ? commendationAndDisciplineDto.getDateCreated() : commendationAndDisciplineDto.getDateChange());
                if (StringUtils.hasText(titleRecruit)) {
                    employeeHistory.setDescription(s);
                    Contract contract = employee.getContract();
                    contract.setBasicSalary(basicSalary);
                    contract.setCoefficientSalary(coefficientSalary);
                    contract.setHourlyRate(hourlyRate);
                    contractRepo.save(contract);
                    employeeHistory.setEvent("Nhân Viên Được Tăng Lương");
                }
                employeeHistory.setStatus(OFFICIAL_STAFF.getType());
                employeeHistory.setEmployeeHistory(modelMapper.map(employeeDto, Employee.class));
            }
            employeeHistoryRepository.save(employeeHistory);
        }
    }
}
