package com.longkubi.qlns.rest;

import com.longkubi.qlns.model.dto.EmployeeDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.EmployeeSearchDto;
import com.longkubi.qlns.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.longkubi.qlns.common.Global.getTokenFromHeaders;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/employees")

public class RetEmployeeController {
    @Autowired
    private IEmployeeService sv;

    @GetMapping()
    public ResponseData<List<EmployeeDto>> getAll() {
        return sv.getAll();
    }

    @GetMapping("/employees-without-contract")
    public ResponseData<List<EmployeeDto>> getEmployeesWithoutContract() {
        return sv.getEmployeesWithoutContract();
    }

    @PostMapping("searchByDto")
    public ResponseData<Page<EmployeeDto>> searchByDto(@RequestBody EmployeeSearchDto searchDto) {
        return sv.searchByDto(searchDto);
    }

    @PostMapping()
    public ResponseData<EmployeeDto> create(@Valid @RequestBody EmployeeDto employeeDto, @RequestHeader HttpHeaders headers) {
        return sv.create(employeeDto, getTokenFromHeaders(headers));
    }

    @PutMapping("/{id}")
    public ResponseData<EmployeeDto> update(@Valid @RequestBody EmployeeDto employeeDto, @PathVariable(name = "id") UUID id, @RequestHeader HttpHeaders headers) {
        return sv.update(employeeDto, id, getTokenFromHeaders(headers));
    }

    @DeleteMapping("/{id}")
    public ResponseData<Boolean> deleteById(@PathVariable(name = "id") UUID id, @RequestHeader HttpHeaders headers) {
        return sv.deleteById(id);
    }
}
