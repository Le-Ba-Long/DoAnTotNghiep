package com.longkubi.qlns.rest;

import com.longkubi.qlns.model.dto.PaymentSalaryDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.TimeKeepingDto;
import com.longkubi.qlns.model.dto.search.PaymentSalarySearchDto;
import com.longkubi.qlns.model.dto.search.TimeKeepingSearchDto;
import com.longkubi.qlns.service.IPaymentSalaryService;
import com.longkubi.qlns.service.ITimeKeepingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/payment-salarys")
public class RestPaymentSalaryController {
    @Autowired
    private IPaymentSalaryService sv;

    @GetMapping()
    public ResponseData<List<PaymentSalaryDto>> getAll() {
        return sv.getAll();
    }

    @GetMapping("/{employeeId}")
    public ResponseData<List<PaymentSalaryDto>> getAllPaymentSalaryByEmployeeId(@PathVariable(name = "employeeId") UUID employeeId) {
        return sv.getAllPaymentSalaryByEmployeeId(employeeId);
    }

    @PostMapping("/searchByDto")
    public ResponseData<Page<PaymentSalaryDto>> searchByDto(@RequestBody PaymentSalarySearchDto searchDto) {
        return sv.searchByDto(searchDto);
    }

    @DeleteMapping("/{id}")
    public ResponseData<Boolean> deleteById(@PathVariable("id") UUID id) {
        return sv.deleteById(id);
    }


}
