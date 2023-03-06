package com.longkubi.qlns.rest;

import com.longkubi.qlns.common.ErrorMessage;
import com.longkubi.qlns.common.ExcelExporterUtil;
import com.longkubi.qlns.model.dto.EmployeeDto;
import com.longkubi.qlns.model.dto.PersonnelChangeReport;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.EmployeeSearchDto;
import com.longkubi.qlns.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.longkubi.qlns.common.Constant.titleBirthDay;
import static com.longkubi.qlns.common.Constant.titleContract;
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

    @GetMapping("/get-personnel-change-report")
    //public ResponseData<Map<YearMonth, Integer>> getPersonnelChangeReport() {
    public ResponseData<List<PersonnelChangeReport>> getPersonnelChangeReport() {
        return sv.getPersonnelChangeReport();
    }

    @GetMapping("/get-monthly-employee-count-report")
    //public ResponseData<Map<YearMonth, Integer>> getPersonnelChangeReport() {
    public ResponseData<List<PersonnelChangeReport>> getMonthlyEmployeeCountReport() {
        return sv.getMonthlyEmployeeCountReport();
    }

    @GetMapping("/get-employee-allocation-ratio-by-department")
    //public ResponseData<Map<YearMonth, Integer>> getPersonnelChangeReport() {
    public ResponseData<List<PersonnelChangeReport>> getEmployeeAllocationRatioByDepartment() {
        return sv.getEmployeeCountByDepartment();
    }

    @GetMapping("/get-employee-birthday-in-range")
    public ResponseData<List<EmployeeDto>> getEmployeeBirthdayInRange(@RequestParam(value = "startMonth") Integer startMonth, @RequestParam(value = "endMonth") Integer endMonth) {
        return sv.getEmployeeBirthdayInRange(startMonth, endMonth);
    }

    @GetMapping("/export-employee-birth-day-in-range")
    public ResponseEntity<byte[]> exportToExcel(@RequestParam(value = "startMonth") Integer startMonth, @RequestParam(value = "endMonth") Integer endMonth) throws IOException {
        List<EmployeeDto> employeeList = sv.getEmployeeBirthdayInRange(startMonth, endMonth).getData();
        // Xuất dữ liệu ra file Excel
        return ExcelExporterUtil.exportToExcel(employeeList, titleBirthDay, "danh-sach-sinh-nhat-nhan-vien-thang( " + startMonth + "-" + endMonth + ")");
    }

    @PostMapping("/export-employees-about-to-expire-contract")
    public ResponseEntity<?> exportEmployeesWithExpiringContractsToExcel(@RequestBody Set<Integer> conditions) throws IOException {
        if (conditions.size() == 0) {
            return new ResponseEntity<>("Danh Sách Rỗng", HttpStatus.NOT_FOUND);
        }
        List<EmployeeDto> employeeList = sv.getEmployeesAboutToExpireContract(conditions).getData();
        if (employeeList.size() == 0) {
            return new ResponseEntity<>("Trạng Thái Không Đúng", HttpStatus.NOT_FOUND);
        }
        // Xuất dữ liệu ra file Excel
        return ExcelExporterUtil.exportToExcel(employeeList, titleContract, "danh-sach-nhan-vien-sap-het-han-hop-dong");
    }
//    @GetMapping("/export-employees-about-to-expire-contract")
//    public ResponseEntity<byte[]> exportEmployeesWithExpiringContractsToExcel(@RequestBody Set<Integer> conditions) throws IOException {
//        List<EmployeeDto> employeeList = sv.getEmployeesAboutToExpireContract(conditions).getData();
//        // Xuất dữ liệu ra file Excel
//        return ExcelExporterUtil.exportToExcel(employeeList, titleContract, "danh-sach-nhan-vien-sap-het-han-hop-dong");
//    }

    @PostMapping("/get-employees-about-to-expire-contract")
    public ResponseData<List<EmployeeDto>> getEmployeesAboutToExpireContract(@RequestBody Set<Integer> conditions) {
        if (conditions.size() == 0) {
            return new ResponseData<>(ErrorMessage.LIST_IS_EMPTY, new ArrayList<>());
        }
        return sv.getEmployeesAboutToExpireContract(conditions);
    }

    @PostMapping("searchByDto")
    public ResponseData<Page<EmployeeDto>> searchByDto(@RequestBody EmployeeSearchDto searchDto) {
        return sv.searchByDto(searchDto);
    }

    @PostMapping()
    public ResponseData<EmployeeDto> create(@Valid @RequestBody EmployeeDto employeeDto, @RequestHeader HttpHeaders
            headers) {
        return sv.create(employeeDto, getTokenFromHeaders(headers));
    }

    @PutMapping("/{id}")
    public ResponseData<EmployeeDto> update(@Valid @RequestBody EmployeeDto
                                                    employeeDto, @PathVariable(name = "id") UUID id, @RequestHeader HttpHeaders headers) {
        return sv.update(employeeDto, id, getTokenFromHeaders(headers));
    }

    @DeleteMapping("/{id}")
    public ResponseData<Boolean> deleteById(@PathVariable(name = "id") UUID id, @RequestHeader HttpHeaders headers) {
        return sv.deleteById(id);
    }
}
