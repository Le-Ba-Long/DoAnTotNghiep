package com.longkubi.qlns.service;

import com.longkubi.qlns.model.dto.PaymentSalaryDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.SalaryDto;
import com.longkubi.qlns.model.dto.SalaryDto;
import com.longkubi.qlns.model.dto.search.PaymentSalarySearchDto;
import com.longkubi.qlns.model.dto.search.SalarySearchDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface IPaymentSalaryService {
    ResponseData<PaymentSalaryDto> create(SalaryDto salaryDto, String token);

    ResponseData<PaymentSalaryDto> update(SalaryDto salaryDto, UUID id, String token);

    ResponseData<List<PaymentSalaryDto>> getAll();

    ResponseData<Page<PaymentSalaryDto>> searchByDto(PaymentSalarySearchDto searchDto);

    ResponseData<Boolean> deleteById(UUID id);

    ResponseData<List<PaymentSalaryDto>> getAllPaymentSalaryByEmployeeId(UUID id);
}
