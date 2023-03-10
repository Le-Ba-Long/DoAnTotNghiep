package com.longkubi.qlns.service.impl;

import com.longkubi.qlns.common.Constant;
import com.longkubi.qlns.common.ErrorMessage;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.TimeKeepingDto;
import com.longkubi.qlns.model.dto.search.TimeKeepingSearchDto;
import com.longkubi.qlns.model.entity.Contract;
import com.longkubi.qlns.model.entity.Employee;
import com.longkubi.qlns.model.entity.PaymentSalary;
import com.longkubi.qlns.model.entity.TimeKeeping;
import com.longkubi.qlns.repository.EmployeeRepository;
import com.longkubi.qlns.repository.PaymentSalaryRepository;
import com.longkubi.qlns.repository.TimeKeepingRepository;
import com.longkubi.qlns.security.jwt.JwtProvider;
import com.longkubi.qlns.service.ITimeKeepingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PrePersist;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.longkubi.qlns.common.Constant.StatusType.NEW_SAVE;
import static com.longkubi.qlns.common.ErrorMessage.*;

@Transactional
@Service
public class TimeKeepingServiceImpl implements ITimeKeepingService {
    @Autowired
    private TimeKeepingRepository repo;
    @Autowired
    private EmployeeRepository employeRepo;
    @Autowired
    private PaymentSalaryRepository paymentSalaryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private EntityManager manager;

    @PrePersist
    public String generateCode() {

        // T???o m?? m???i ng???u nhi??n t??? 0001 ?????n 9999
        String nextCode = String.format("MaCC%04d", new Random().nextInt(9999) + 1);
        // Ki???m tra t??nh duy nh???t c???a m??
        while (!isCodeUnique(nextCode)) {
            nextCode = String.format("MaCC%04d", new Random().nextInt(9999) + 1);
        }
        return nextCode;
    }

    // Ph????ng th???c ki???m tra t??nh duy nh???t c???a m??
    private boolean isCodeUnique(String code) {
        // S??? d???ng EntityManager ????? ki???m tra t??nh duy nh???t c???a m?? trong c?? s??? d??? li???u
        try {
            // T??m ?????i t?????ng TimeKeeping c?? tr?????ng code b???ng v???i code ???????c truy???n v??o
            TypedQuery<TimeKeeping> query = manager.createQuery("SELECT tk FROM TimeKeeping tk WHERE tk.code = :code", TimeKeeping.class);
            query.setParameter("code", code);
            List<TimeKeeping> result = query.getResultList();
            // N???u kh??ng c?? b???n ghi n??o c?? c??ng m??, tr??? v??? true
            return result.isEmpty();
        } finally {
            manager.close();
        }
    }

    @Override
    public ResponseData<TimeKeepingDto> create(TimeKeepingDto timeKeepingDto, String token) {

        ErrorMessage errorMessage = validateTimeKeeping(timeKeepingDto, null, Constant.Insert);
        if (!errorMessage.equals(SUCCESS)) return new ResponseData<>(errorMessage, null);
        TimeKeeping entity = new TimeKeeping();
        modelMapper.map(timeKeepingDto, entity);
        entity.setCode(generateCode());
        Employee employee = employeRepo.getEmployeeById(timeKeepingDto.getEmployee().getId());
        Contract contractDto = employee.getContract();
        int hourlyRate = contractDto.getHourlyRate();// s??? ti???n t??nh cho 1 gi??? l??m th??m
        float coefficientSalary = contractDto.getCoefficientSalary();//h??? s??? l????ng
        int basicSalary = contractDto.getBasicSalary();//m???c l????ng c?? b???n
        //T??nh L????ng
        PaymentSalary paymentSalary = salaryCalculation(entity, timeKeepingDto, basicSalary, coefficientSalary, hourlyRate, token, Constant.Insert);
        paymentSalary.setTimeKeeping(repo.save(entity));
        paymentSalaryRepository.save(paymentSalary);
        //  entity.setEmployee(employeRepo.getEmployeeById(timeKeepingDto.getEmployee().getId()));
        //  return new ResponseData<>(modelMapper.map(repo.save(entity), TimeKeepingDto.class));
        return new ResponseData<>(modelMapper.map(entity, TimeKeepingDto.class));
    }

    @Override
    public ResponseData<TimeKeepingDto> update(TimeKeepingDto timeKeepingDto, UUID id, String token) {

        ErrorMessage errorMessage = validateTimeKeeping(timeKeepingDto, id, Constant.Update);
        if (!errorMessage.equals(SUCCESS)) return new ResponseData<>(errorMessage, null);
        if (Boolean.FALSE.equals(repo.existsTimeKeepingById(id))) return new ResponseData<>(ID_NOT_EXIST, null);
        TimeKeeping entity = repo.getTimeKeepingById(id);
        entity.setCode(timeKeepingDto.getCode());
        // entity.setEmployee(employeRepo.getEmployeeById(timeKeepingDto.getEmployee().getId()));
        entity.setNumberWorkDay(timeKeepingDto.getNumberWorkDay());
        entity.setNumberDayOff(timeKeepingDto.getNumberDayOff());
        entity.setNumberDayUnexcusedLeave(timeKeepingDto.getNumberDayUnexcusedLeave());
        entity.setNumberOvertimeHours(timeKeepingDto.getNumberOvertimeHours());
        entity.setMonth(timeKeepingDto.getMonth());
        entity.setYear(timeKeepingDto.getYear());
        entity.setStatus(timeKeepingDto.getStatus());
        Employee employee = employeRepo.getEmployeeById(timeKeepingDto.getEmployee().getId());
        Contract contractDto = employee.getContract();
        int hourlyRate = contractDto.getHourlyRate();// s??? ti???n t??nh cho 1 gi??? l??m th??m
        float coefficientSalary = contractDto.getCoefficientSalary();//h??? s??? l????ng
        int basicSalary = contractDto.getBasicSalary();//m???c l????ng c?? b???n
        // t??nh l????ng
        entity.setPaymentSalary(salaryCalculation(entity, timeKeepingDto, basicSalary, coefficientSalary, hourlyRate, token, Constant.Update));
        //k???t th??c t??nh l????ng
        entity.setChangedBy(jwtProvider.getUserNameFromToken(token));
        entity.setDateChange(new Date());
        //  entity.setEmployee(employeRepo.getEmployeeById(timeKeepingDto.getEmployee().getId()));
        return new ResponseData<>(modelMapper.map(repo.save(entity), TimeKeepingDto.class));
    }

    @Override
    public ResponseData<List<TimeKeepingDto>> getAll() {
        // List<TimeKeeping> timeKeepings = repo.findAll();
        List<TimeKeeping> timeKeepings = repo.getAll();
        if (timeKeepings.isEmpty()) return new ResponseData<>(SUCCESS, new ArrayList<>());
        return new ResponseData<>(timeKeepings.stream().map(dto -> modelMapper.map(dto, TimeKeepingDto.class)).collect(Collectors.toList()));

    }

    @Override
    public ResponseData<Page<TimeKeepingDto>> searchByDto(TimeKeepingSearchDto searchDto) {
        if (Objects.isNull(searchDto)) return new ResponseData<>(OBJECT_CANNOT_EMPTY, null);
        Integer pageIndex = searchDto.getPageIndex();
        Integer pageSize = searchDto.getPageSize();
        if (pageIndex > 0) {
            pageIndex--;
        } else {
            pageIndex = 0;
        }
        StringBuilder sqlCount = new StringBuilder("SELECT COUNT(entity.id) FROM TimeKeeping entity WHERE (1=1) ");
        StringBuilder sql = new StringBuilder("SELECT entity FROM TimeKeeping entity WHERE (1=1) ");
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
        List<TimeKeeping> timeKeepings = querySql.getResultList();
        long count = (long) queryCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        // return new ResponseData<>(new PageImpl<>(departmentList, pageable, count));
        Page<TimeKeepingDto> result = new PageImpl<>(timeKeepings.stream().map(entity -> modelMapper.map(entity, TimeKeepingDto.class)).collect(Collectors.toList()));
        if (result.isEmpty()) return new ResponseData<>(SUCCESS, new PageImpl<>(new ArrayList<>()));
        return new ResponseData<>(result);
    }

    private String genOrderByClause(TimeKeepingSearchDto dto) {
        if (dto.getOrderByFilter() != null && StringUtils.hasText(dto.getOrderByFilter())) {
            switch (dto.getOrderByFilter()) {
                case "code ASC":
                    return "ORDER BY entity.code ASC";
                case "code DESC":
                    return "ORDER BY entity.code DESC";
                case "DateChange ASC":
                    return "ORDER BY entity.dateChange ASC";
                case "DateChange DESC":
                    return "ORDER BY entity.dateChange DESC";
            }
        }
        return "";
    }

    private void setParameter(Query query, TimeKeepingSearchDto searchDto) {
        if (!Objects.isNull(searchDto.getId())) {
            query.setParameter("id", searchDto.getId());
        }
        if (StringUtils.hasText(searchDto.getCode())) {
            query.setParameter("code", searchDto.getCode());
        }

        if (!Objects.isNull(searchDto.getStatus())) {
            query.setParameter("status", searchDto.getStatus());
        }
        if (!Objects.isNull(searchDto.getMonth())) {
            query.setParameter("month", searchDto.getMonth());
        }
        if (!Objects.isNull(searchDto.getYear())) {
            query.setParameter("year", searchDto.getYear());
        }
        if (!Objects.isNull(searchDto.getEmployeeId())) {
            query.setParameter("employeeId", searchDto.getEmployeeId());
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

    private String genWhereClause(TimeKeepingSearchDto searchDto) {
        StringBuilder whereClause = new StringBuilder();
        if (!Objects.isNull(searchDto.getId())) {
            whereClause.append(" AND (entity.id = :id)");
        }
        if (!Objects.isNull(searchDto.getEmployeeId())) {
            whereClause.append(" AND (entity.employee.id = :employeeId)");
        }
        if (StringUtils.hasText(searchDto.getCode())) {
            whereClause.append(" AND (entity.code LIKE :code)");
        }

        if (!Objects.isNull(searchDto.getNumberDayOff())) {
            whereClause.append(" AND (entity.numberWorkDay = :numberWorkDay)");
        }
        if (!Objects.isNull(searchDto.getNumberDayOff())) {
            whereClause.append(" AND (entity.numberDayOff = :numberDayOff)");
        }
        if (!Objects.isNull(searchDto.getNumberDayUnexcusedLeave())) {
            whereClause.append(" AND (entity.numberDayUnexcusedLeave = :numberDayUnexcusedLeave)");
        }

        if (!Objects.isNull(searchDto.getMonth())) {
            whereClause.append(" AND (entity.month = :month)");
        }
        if (!Objects.isNull(searchDto.getYear())) {
            whereClause.append(" AND (entity.year = :year)");
        }
        if (!Objects.isNull(searchDto.getNumberOvertimeHours())) {
            whereClause.append(" AND (entity.numberOvertimeHours = :numberOvertimeHours)");
        }
        if (!Objects.isNull(searchDto.getStatus())) {
            whereClause.append(" AND (entity.status = :status)");
        }
        if (StringUtils.hasText(searchDto.getCreator())) {
            whereClause.append(" AND (entity.creator = :creator)");
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
        if (Boolean.TRUE.equals(repo.existsTimeKeepingById(id))) {
            repo.deleteById(id);
            return new ResponseData<>(SUCCESS, true);
        } else {
            return new ResponseData<>(ID_NOT_EXIST, false);
        }
    }

    private ErrorMessage validateTimeKeeping(TimeKeepingDto timeKeepingDto, UUID id, String action) {
        if (Constant.Insert.equals(action)) {
            if (Objects.isNull(timeKeepingDto)) return OBJECT_CANNOT_EMPTY;
            if (Boolean.TRUE.equals(repo.existsTimeKeepingByCode(timeKeepingDto.getCode()))) return CODE_ALREADY_EXIST;
            // if (Boolean.TRUE.equals(repo.existsCertificateByName(certificateDto.getName()))) return NAME_EXIST;
            if (repo.existsTimeKeepingByMonthAndYear(timeKeepingDto.getMonth(), timeKeepingDto.getYear(), timeKeepingDto.getEmployee().getId()))
                return MONTH_AND_YEAR_EXIST;
        } else {
            if (Objects.isNull(timeKeepingDto)) return OBJECT_CANNOT_EMPTY;
            if (repo.exclusionCode(timeKeepingDto.getCode(), id) > 0) return CODE_ALREADY_EXIST;
            if (repo.isValidMonthYearExcludingCurrent(timeKeepingDto.getMonth(), timeKeepingDto.getYear(), timeKeepingDto.getEmployee().getId(), id))
                return MONTH_AND_YEAR_EXIST;
            //  if (repo.exclusionName(certificateDto.getName(), id) > 0) return NAME_EXIST;
        }
        return SUCCESS;
    }

    private float getTheValueOfpPersonalIncomeTax(BigDecimal totalIncome) {
        if (totalIncome.compareTo(BigDecimal.valueOf(5000000)) < 0) {
            return 0.0F;//0%
        } else if (totalIncome.compareTo(BigDecimal.valueOf(5000000)) >= 0 && totalIncome.compareTo(BigDecimal.valueOf(10000000)) < 0) {
            return 0.05F;//5%
        } else if (totalIncome.compareTo(BigDecimal.valueOf(10000000)) >= 0 && totalIncome.compareTo(BigDecimal.valueOf(18000000)) < 0) {
            return 0.1F;//10%
        } else if (totalIncome.compareTo(BigDecimal.valueOf(18000000)) >= 0 && totalIncome.compareTo(BigDecimal.valueOf(32000000)) < 0) {
            return 0.15F;//15%
        } else if (totalIncome.compareTo(BigDecimal.valueOf(32000000)) >= 0 && totalIncome.compareTo(BigDecimal.valueOf(52000000)) < 0) {
            return 0.2F;//20%
        } else if (totalIncome.compareTo(BigDecimal.valueOf(52000000)) >= 0 && totalIncome.compareTo(BigDecimal.valueOf(80000000)) < 0) {
            return 0.25F;//25%
        } else {
            return 0.3F;//30%
        }

    }

    // t??nh l????ng cho nh??n vi??n
    private PaymentSalary salaryCalculation(TimeKeeping entity, TimeKeepingDto timeKeepingDto, int basicSalary, float coefficientSalary, int hourlyRate, String token, String action) {
        //T??nh L????ng
        PaymentSalary paymentSalary;
        if (Constant.Insert.equals(action)) {
            paymentSalary = new PaymentSalary();
            paymentSalary.setCreator(jwtProvider.getUserNameFromToken(token));
            paymentSalary.setDateCreated(new Date());
        } else {
            paymentSalary = entity.getPaymentSalary();
            paymentSalary.setChangedBy(jwtProvider.getUserNameFromToken(token));
            paymentSalary.setDateChange(new Date());
        }

        double payForOneDayOfWork = basicSalary / 23.0;// s??? ti???n t??nh cho 1 ng??y l??m vi???c
        int diligence = 500000; // ti???n l????ng chuy??n c???n khi ??i l??m ?????y ?????
        int subsidize = 1500000; // tr??? c???p x??ng xe v?? ??n tr??a
        //long insurance = 800000L;// ti???n b???o hi???m
        BigDecimal totalSalary = new BigDecimal("0.0");
        if (timeKeepingDto.getNumberWorkDay() != null) {
            byte numberWorkDay = timeKeepingDto.getNumberWorkDay();// S??? ng??y c??ng ??i l??m
            totalSalary = totalSalary.add(new BigDecimal(numberWorkDay * payForOneDayOfWork));// t??nh ti???n cho t???ng s??? ng??y c??ng ??i l??m trong th??ng
            // totalSalary = (double) (numberWorkDay * payForOneDayOfWork);// t??nh ti???n cho s??? ng??y c??ng ??i l??m trong th??ng
            if (numberWorkDay == 23) {
                //t??nh th??m ti???n l????ng chuy??n c???n khi ??i ?????y ?????
                totalSalary = totalSalary.add(new BigDecimal(diligence));
                paymentSalary.setFullTimeSalary(diligence);
            } else {
                // n???u kh??ng ??i ????? 23 ng??y c??ng th?? chuy??n c???n s??? kh??ng ???????c t??nh.
                paymentSalary.setFullTimeSalary(0);
            }
            if (timeKeepingDto.getNumberDayOff() != 0) {
                byte numberDayOff = timeKeepingDto.getNumberDayOff();// S??? ng??y ngh??? c?? ph??p
                if (numberDayOff <= 3) {
                    totalSalary = totalSalary.add(new BigDecimal((numberDayOff * payForOneDayOfWork) * 0.5));// t??nh ti???n l????ng h?????ng 50% cho ng??y ngh??? ph??p
                    //totalSalary += (numberDayOff * payForOneDayOfWork * 0.5);// t??nh ti???n l????ng h?????ng 50% cho ng??y ngh??? ph??p
                }
            }
            if (timeKeepingDto.getNumberDayUnexcusedLeave() != 0) {
                byte numberDayUnexcusedLeave = timeKeepingDto.getNumberDayUnexcusedLeave();// S??? ng??y ngh??? kh??ng ph??p
                if (numberDayUnexcusedLeave <= 2) {
                    totalSalary = totalSalary.add(new BigDecimal(subsidize));//t??nh th??m ti???n tr??? c???p n???u ngh??? kh??ng qu?? 2 ng??y ngh??? kh??ng ph??p
                    paymentSalary.setTransportationAndLunchAllowance(subsidize);
                    // totalSalary += subsidize;//t??nh th??m ti???n tr??? c???p n???u ngh??? kh??ng qu?? 2 ng??y ngh??? kh??ng ph??p
                } else {
                    //n???u ngh??? qu?? >=3 ng??y ph??p th?? kh??ng ???????c nh???n tr??? c???p x??ng xe v?? ??n tr??a
                    paymentSalary.setTransportationAndLunchAllowance(0);
                }
            } else {
                totalSalary = totalSalary.add(new BigDecimal(subsidize));//t??nh th??m ti???n tr??? c???p n???u kh??ng c?? ng??y ngh??? kh??ng ph??p n??o
                paymentSalary.setTransportationAndLunchAllowance(subsidize);
                // totalSalary += subsidize;//t??nh th??m ti???n tr??? c???p
            }
            if (timeKeepingDto.getNumberOvertimeHours() != 0) {
                short numberOvertimeHours = timeKeepingDto.getNumberOvertimeHours();//s??? gi??? l??m t??ng ca
                totalSalary = totalSalary.add(new BigDecimal(numberOvertimeHours * hourlyRate));
                // totalSalary += numberOvertimeHours * hourlyRate;
            }
        }
        //// t???ng l????ng ??i l??m dc nh??n v???i h??? s??? l????ng trong h???p ?????ng
        totalSalary = totalSalary.multiply(new BigDecimal(coefficientSalary));
        float valueAddedWithEachSalary = getTheValueOfpPersonalIncomeTax(totalSalary);// gi?? tr??? gia t??ng thu??? thu nh???p c?? nh??n d???a v??o m???c l????ng c???a nh??n vi??n
        float socialInsuranceCosts = totalSalary.floatValue() * 0.08F;// t??nh ti???n b???o hi???m x?? h???i 8% t???ng thu  nh???p c???a nh??n vi??n
        float healthInsurancePremium = totalSalary.floatValue() * 0.015F;// t??nh ti???n b???o hi???m y t??? 1.5% t???ng thu  nh???p c???a nh??n vi??n

        float personalIncomeTax = (totalSalary.floatValue() - socialInsuranceCosts - healthInsurancePremium) * valueAddedWithEachSalary;
        // t??nh t???ng l????ng  th???c l??nh = t???ng thu nh???p - b???o hi???m x?? h???i - b???o hi???m y t??? - thu??? thu nh???p c?? nh??n ;
        BigDecimal netWage = totalSalary.subtract(new BigDecimal(socialInsuranceCosts)).subtract(new BigDecimal(healthInsurancePremium)).subtract(new BigDecimal(personalIncomeTax));
        // totalSalary = (totalSalary * coefficientSalary) - insurance;//t???ng l????ng nh??n v???i h??? s??? tr??? ??i ti???n b???o hi???m
        // l????ng th???c l??nh
        paymentSalary.setValueAddedWithEachSalary(valueAddedWithEachSalary);// gi?? tr??? gia t??ng thu??? thu nh???p c?? nh??n theo t???ng thu nh???p c???a nh??n vi??n
        paymentSalary.setPersonalIncomeTax(personalIncomeTax);
        paymentSalary.setHealthInsurancePremium(healthInsurancePremium);// t??nh ti???n b???o hi???m y t???
        paymentSalary.setSocialInsuranceCosts(socialInsuranceCosts);// t??nh ti???n b???o hi???m x?? h???i
        paymentSalary.setNetWage(netWage);
        paymentSalary.setMonth(timeKeepingDto.getMonth());
        paymentSalary.setYear(timeKeepingDto.getYear());
        paymentSalary.setStatus(NEW_SAVE.getType());
        if (Constant.Insert.equals(action)) {
            paymentSalary.setCreator(jwtProvider.getUserNameFromToken(token));
            paymentSalary.setDateCreated(new Date());
        } else {
            paymentSalary.setChangedBy(jwtProvider.getUserNameFromToken(token));
            paymentSalary.setDateChange(new Date());
        }

        //  paymentSalary.setTimeKeeping(entity);
        return paymentSalary;
    }
}
