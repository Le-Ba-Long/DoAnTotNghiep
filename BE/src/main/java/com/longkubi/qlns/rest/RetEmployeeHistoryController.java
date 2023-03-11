package com.longkubi.qlns.rest;

import com.longkubi.qlns.model.dto.EmployeeDto;
import com.longkubi.qlns.model.dto.EmployeeHistoryDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.entity.EmployeeHistory;
import com.longkubi.qlns.service.IEmployeeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static com.longkubi.qlns.common.Global.getTokenFromHeaders;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/employee-history")

public class RetEmployeeHistoryController {
    @Autowired
    private IEmployeeHistoryService sv;

    @GetMapping()
    public ResponseData<List<EmployeeHistoryDto>> getAll() {
        return sv.getAll();
    }

    @GetMapping("get-employee-history-by-id/{employeeId}")
    public ResponseData<List<EmployeeHistoryDto>> getEmployeeHistoryByID(@PathVariable(value = "employeeId") UUID employeeID) {
        return sv.getEmployeeHistoryByID(employeeID);
    }

    @PostMapping()
    public ResponseData<EmployeeHistoryDto> create(@Valid @RequestBody EmployeeHistoryDto employeeHistoryDto) {
        return sv.create(employeeHistoryDto);
    }
}
