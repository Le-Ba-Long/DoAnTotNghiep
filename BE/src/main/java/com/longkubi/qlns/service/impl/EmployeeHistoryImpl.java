package com.longkubi.qlns.service.impl;

import com.longkubi.qlns.model.dto.EmployeeHistoryDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.entity.EmployeeHistory;
import com.longkubi.qlns.repository.EmployeeHistoryRepository;
import com.longkubi.qlns.service.IEmployeeHistoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.longkubi.qlns.common.ErrorMessage.SUCCESS;

@Transactional
@Service
public class EmployeeHistoryImpl implements IEmployeeHistoryService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EmployeeHistoryRepository repo;

    @Override
    public ResponseData<List<EmployeeHistoryDto>> getAll() {
        // List<Employee> employeeDtos = repo.findAll();
        List<EmployeeHistory> employeeHistories = repo.findAll();
        if (employeeHistories.isEmpty()) return new ResponseData<>(SUCCESS, new ArrayList<>());
        return new ResponseData<>(employeeHistories.stream().map(dto -> modelMapper.map(dto, EmployeeHistoryDto.class)).collect(Collectors.toList()));

    }

    @Override
    public ResponseData<EmployeeHistoryDto> create(EmployeeHistoryDto employeeHistoryDto) {
        EmployeeHistory entity = new EmployeeHistory();
        modelMapper.map(employeeHistoryDto, entity);
        ;
        return new ResponseData<>(modelMapper.map(repo.save(entity), EmployeeHistoryDto.class));
    }

    @Override
    public ResponseData<List<EmployeeHistoryDto>> getEmployeeHistoryByID(UUID employeeID) {
        return new ResponseData<>(repo.getEmployeeHistoryByID(employeeID).stream().map(employeeHistory -> modelMapper.map(employeeHistory, EmployeeHistoryDto.class)).collect(Collectors.toList()));
    }
}
