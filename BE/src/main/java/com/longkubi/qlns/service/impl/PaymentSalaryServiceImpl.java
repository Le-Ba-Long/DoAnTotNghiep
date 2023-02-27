package com.longkubi.qlns.service.impl;

import com.longkubi.qlns.model.dto.PaymentSalaryDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.SalaryDto;
import com.longkubi.qlns.model.dto.search.PaymentSalarySearchDto;
import com.longkubi.qlns.model.entity.PaymentSalary;
import com.longkubi.qlns.repository.EmployeeRepository;
import com.longkubi.qlns.repository.PaymentSalaryRepository;
import com.longkubi.qlns.security.jwt.JwtProvider;
import com.longkubi.qlns.service.IPaymentSalaryService;
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
public class PaymentSalaryServiceImpl implements IPaymentSalaryService {
    @Autowired
    private PaymentSalaryRepository repo;
    @Autowired
    private EmployeeRepository employeRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private EntityManager manager;

    @Override
    public ResponseData<PaymentSalaryDto> create(SalaryDto salaryDto, String token) {
        return null;
    }
    @Override
    public ResponseData<PaymentSalaryDto> update(SalaryDto salaryDto, UUID id, String token) {
        return null;
    }
    @Override
    public ResponseData<List<PaymentSalaryDto>> getAll() {
        //  List<PaymentSalary> paymentSalaries = repo.findAll();
        List<PaymentSalary> paymentSalaries = repo.getAll();
        if (paymentSalaries.isEmpty()) return new ResponseData<>(SUCCESS, new ArrayList<>());
        return new ResponseData<>(paymentSalaries.stream().map(dto -> modelMapper.map(dto, PaymentSalaryDto.class)).collect(Collectors.toList()));

    }
    @Override
    public ResponseData<Boolean> deleteById(UUID id) {
        if (Boolean.TRUE.equals(repo.existsPaymentSalaryById(id))) {
            repo.deleteById(id);
            return new ResponseData<>(SUCCESS, true);
        } else {
            return new ResponseData<>(ID_NOT_EXIST, false);
        }
    }
    @Override
    public ResponseData<List<PaymentSalaryDto>> getAllPaymentSalaryByEmployeeId(UUID id) {
        List<PaymentSalary> paymentSalaries = repo.getAllPaymentSalaryByEmployeeId(id);
        if (paymentSalaries.isEmpty()) return new ResponseData<>(SUCCESS, new ArrayList<>());
        return new ResponseData<>(paymentSalaries.stream().map(dto ->
                modelMapper.map(dto, PaymentSalaryDto.class)).collect(Collectors.toList()));
    }
    @Override
    public ResponseData<Page<PaymentSalaryDto>> searchByDto(PaymentSalarySearchDto searchDto) {
        if (Objects.isNull(searchDto)) return new ResponseData<>(OBJECT_CANNOT_EMPTY, null);
        Integer pageIndex = searchDto.getPageIndex();
        Integer pageSize = searchDto.getPageSize();
        if (pageIndex > 0) {
            pageIndex--;
        } else {
            pageIndex = 0;
        }
        StringBuilder sqlCount = new StringBuilder("select count(p.id) from PaymentSalary p join TimeKeeping t on p.timeKeeping.id = t.id JOIN Employee e on e.id = t.employee.id where(1=1)  ");
        StringBuilder sql = new StringBuilder("select p from PaymentSalary p join TimeKeeping t on p.timeKeeping.id = t.id JOIN Employee e on e.id = t.employee.id where(1=1)  ");
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
        List<PaymentSalary> paymentSalaries = querySql.getResultList();
        long count = (long) queryCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        // return new ResponseData<>(new PageImpl<>(departmentList, pageable, count));
        Page<PaymentSalaryDto> result = new PageImpl<>(paymentSalaries.stream().map(entity -> modelMapper.map(entity, PaymentSalaryDto.class)).collect(Collectors.toList()));
        if (result.isEmpty()) return new ResponseData<>(SUCCESS, new PageImpl<>(new ArrayList<>()));
        return new ResponseData<>(result);
    }
    private String genOrderByClause(PaymentSalarySearchDto dto) {
        if (dto.getOrderByFilter() != null && StringUtils.hasText(dto.getOrderByFilter())) {
            switch (dto.getOrderByFilter()) {
                case "DateChange ASC":
                    return "ORDER BY entity.dateChange ASC";
                case "DateChange DESC":
                    return "ORDER BY entity.dateChange DESC";
            }
        }
        return "";
    }

    private void setParameter(Query query, PaymentSalarySearchDto searchDto) {
        if (!Objects.isNull(searchDto.getId())) {
            query.setParameter("id", searchDto.getId());
        }
        if (!Objects.isNull(searchDto.getEmployeeId())) {
            query.setParameter("employeeId", searchDto.getEmployeeId());
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

    private String genWhereClause(PaymentSalarySearchDto searchDto) {
        StringBuilder whereClause = new StringBuilder();
        if (!Objects.isNull(searchDto.getId())) {
            whereClause.append(" AND (p.id = :id)");
        }
        if (!Objects.isNull(searchDto.getEmployeeId())) {
            whereClause.append(" AND (e.id = :employeeId)");
        }
        if (!Objects.isNull(searchDto.getMonth())) {
            whereClause.append(" AND (p.month = :month)");
        }
        if (!Objects.isNull(searchDto.getYear())) {
            whereClause.append(" AND (p.year = :year)");
        }
        if (!Objects.isNull(searchDto.getStatus())) {
            whereClause.append(" AND (p.status = :status)");
        }
        if (StringUtils.hasText(searchDto.getCreator())) {
            whereClause.append(" AND (p.creator = :creator)");
        }
        if (!Objects.isNull(searchDto.getDateCreated())) {
            whereClause.append(" AND (p.dateCreated >= :dateCreated)");
        }
        if (StringUtils.hasText(searchDto.getChangedBy())) {
            whereClause.append(" AND (p.changedBy = :changedBy)");
        }
        if (!Objects.isNull(searchDto.getDateChange())) {
            whereClause.append(" AND (p.dateChange >= :dateChange)");
        }
        return whereClause.toString();
    }

}
