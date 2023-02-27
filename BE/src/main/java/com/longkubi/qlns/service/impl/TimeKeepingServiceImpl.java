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

        // Tạo mã mới ngẫu nhiên từ 0001 đến 9999
        String nextCode = String.format("MaCC%04d", new Random().nextInt(9999) + 1);
        // Kiểm tra tính duy nhất của mã
        while (!isCodeUnique(nextCode)) {
            nextCode = String.format("MaCC%04d", new Random().nextInt(9999) + 1);
        }
        return nextCode;
    }

    // Phương thức kiểm tra tính duy nhất của mã
    private boolean isCodeUnique(String code) {
        // Sử dụng EntityManager để kiểm tra tính duy nhất của mã trong cơ sở dữ liệu
        try {
            // Tìm đối tượng TimeKeeping có trường code bằng với code được truyền vào
            TypedQuery<TimeKeeping> query = manager.createQuery("SELECT tk FROM TimeKeeping tk WHERE tk.code = :code", TimeKeeping.class);
            query.setParameter("code", code);
            List<TimeKeeping> result = query.getResultList();
            // Nếu không có bản ghi nào có cùng mã, trả về true
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
        int hourlyRate = contractDto.getHourlyRate();// số tiền tính cho 1 giờ làm thêm
        float coefficientSalary = contractDto.getCoefficientSalary();//hệ số lương
        int basicSalary = contractDto.getBasicSalary();//mức lương cơ bản
        //Tính Lương
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
        int hourlyRate = contractDto.getHourlyRate();// số tiền tính cho 1 giờ làm thêm
        float coefficientSalary = contractDto.getCoefficientSalary();//hệ số lương
        int basicSalary = contractDto.getBasicSalary();//mức lương cơ bản
        // tính lương
        entity.setPaymentSalary(salaryCalculation(entity, timeKeepingDto, basicSalary, coefficientSalary, hourlyRate, token, Constant.Update));
        //kết thúc tính lương
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

    // tính lương cho nhân viên
    private PaymentSalary salaryCalculation(TimeKeeping entity, TimeKeepingDto timeKeepingDto, int basicSalary, float coefficientSalary, int hourlyRate, String token, String action) {
        //Tính Lương
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

        double payForOneDayOfWork = basicSalary / 23.0;// số tiền tính cho 1 ngày làm việc
        int diligence = 500000; // tiền lương chuyên cần khi đi làm đầy đủ
        int subsidize = 1500000; // trợ cấp xăng xe và ăn trưa
        //long insurance = 800000L;// tiền bảo hiểm
        BigDecimal totalSalary = new BigDecimal("0.0");
        if (timeKeepingDto.getNumberWorkDay() != null) {
            byte numberWorkDay = timeKeepingDto.getNumberWorkDay();// Số ngày công đi làm
            totalSalary = totalSalary.add(new BigDecimal(numberWorkDay * payForOneDayOfWork));// tính tiền cho tổng số ngày công đi làm trong tháng
            // totalSalary = (double) (numberWorkDay * payForOneDayOfWork);// tính tiền cho số ngày công đi làm trong tháng
            if (numberWorkDay == 23) {
                //tính thêm tiền lương chuyên cần khi đi đầy đủ
                totalSalary = totalSalary.add(new BigDecimal(diligence));
                paymentSalary.setFullTimeSalary(diligence);
            } else {
                // nếu không đi đủ 23 ngày công thì chuyên cần sẽ không được tính.
                paymentSalary.setFullTimeSalary(0);
            }
            if (timeKeepingDto.getNumberDayOff() != 0) {
                byte numberDayOff = timeKeepingDto.getNumberDayOff();// Số ngày nghỉ có phép
                if (numberDayOff <= 3) {
                    totalSalary = totalSalary.add(new BigDecimal((numberDayOff * payForOneDayOfWork) * 0.5));// tính tiền lương hưởng 50% cho ngày nghỉ phép
                    //totalSalary += (numberDayOff * payForOneDayOfWork * 0.5);// tính tiền lương hưởng 50% cho ngày nghỉ phép
                }
            }
            if (timeKeepingDto.getNumberDayUnexcusedLeave() != 0) {
                byte numberDayUnexcusedLeave = timeKeepingDto.getNumberDayUnexcusedLeave();// Số ngày nghỉ không phép
                if (numberDayUnexcusedLeave <= 2) {
                    totalSalary = totalSalary.add(new BigDecimal(subsidize));//tính thêm tiền trợ cấp nếu nghỉ không quá 2 ngày nghỉ không phép
                    paymentSalary.setTransportationAndLunchAllowance(subsidize);
                    // totalSalary += subsidize;//tính thêm tiền trợ cấp nếu nghỉ không quá 2 ngày nghỉ không phép
                } else {
                    //nếu nghỉ quá >=3 ngày phép thì không được nhận trợ cấp xăng xe và ăn trưa
                    paymentSalary.setTransportationAndLunchAllowance(0);
                }
            } else {
                totalSalary = totalSalary.add(new BigDecimal(subsidize));//tính thêm tiền trợ cấp nếu không có ngày nghỉ không phép nào
                paymentSalary.setTransportationAndLunchAllowance(subsidize);
                // totalSalary += subsidize;//tính thêm tiền trợ cấp
            }
            if (timeKeepingDto.getNumberOvertimeHours() != 0) {
                short numberOvertimeHours = timeKeepingDto.getNumberOvertimeHours();//số giờ làm tăng ca
                totalSalary = totalSalary.add(new BigDecimal(numberOvertimeHours * hourlyRate));
                // totalSalary += numberOvertimeHours * hourlyRate;
            }
        }
        //// tổng lương đi làm dc nhân với hệ số lương trong hợp đồng
        totalSalary = totalSalary.multiply(new BigDecimal(coefficientSalary));
        float valueAddedWithEachSalary = getTheValueOfpPersonalIncomeTax(totalSalary);// giá trị gia tăng thuế thu nhập cá nhân dựa vào mức lương của nhân viên
        float socialInsuranceCosts = totalSalary.floatValue() * 0.08F;// tính tiền bảo hiểm xã hội 8% tổng thu  nhập của nhân viên
        float healthInsurancePremium = totalSalary.floatValue() * 0.015F;// tính tiền bảo hiểm y tế 1.5% tổng thu  nhập của nhân viên

        float personalIncomeTax = (totalSalary.floatValue() - socialInsuranceCosts - healthInsurancePremium) * valueAddedWithEachSalary;
        // tính tổng lương  thực lĩnh = tổng thu nhập - bảo hiểm xã hội - bảo hiểm y tế - thuế thu nhập cá nhân ;
        BigDecimal netWage = totalSalary.subtract(new BigDecimal(socialInsuranceCosts)).subtract(new BigDecimal(healthInsurancePremium)).subtract(new BigDecimal(personalIncomeTax));
        // totalSalary = (totalSalary * coefficientSalary) - insurance;//tổng lương nhân với hệ số trừ đi tiền bảo hiểm
        // lương thực lĩnh
        paymentSalary.setValueAddedWithEachSalary(valueAddedWithEachSalary);// giá trị gia tăng thuế thu nhập cá nhân theo tổng thu nhập của nhân viên
        paymentSalary.setPersonalIncomeTax(personalIncomeTax);
        paymentSalary.setHealthInsurancePremium(healthInsurancePremium);// tính tiền bảo hiểm y tế
        paymentSalary.setSocialInsuranceCosts(socialInsuranceCosts);// tính tiền bảo hiểm xã hội
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
