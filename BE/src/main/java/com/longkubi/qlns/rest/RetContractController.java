package com.longkubi.qlns.rest;

import com.longkubi.qlns.model.dto.ContractDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.ContractSearchDto;
import com.longkubi.qlns.model.dto.search.EmployeeSearchDto;
import com.longkubi.qlns.service.IContractService;
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
@RequestMapping("api/contracts")

public class RetContractController {
    @Autowired
    private IContractService sv;


    @GetMapping()
    public ResponseData<List<ContractDto>> getAll() {
        return sv.getAll();
    }

    @PostMapping("searchByDto")
    public ResponseData<Page<ContractDto>> searchByDto(@RequestBody ContractSearchDto searchDto) {
        return sv.searchByDto(searchDto);
    }

    @PostMapping()
    public ResponseData<ContractDto> create(@Valid @RequestBody ContractDto contractDto, @RequestHeader HttpHeaders headers) {
        return sv.create(contractDto, getTokenFromHeaders(headers));
    }

    @PutMapping("/{id}")
    public ResponseData<ContractDto> update(@Valid @RequestBody ContractDto contractDto, @PathVariable(name = "id") UUID id, @RequestHeader HttpHeaders headers) {
        return sv.update(contractDto, id, getTokenFromHeaders(headers));
    }

    @DeleteMapping("/{id}")
    public ResponseData<Boolean> deleteById(@PathVariable(name = "id") UUID id, @RequestHeader HttpHeaders headers) {
        return sv.deleteById(id);
    }
}
