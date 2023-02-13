package com.longkubi.qlns.rest;

import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.SalaryDto;
import com.longkubi.qlns.model.dto.search.SalarySearchDto;
import com.longkubi.qlns.service.ISalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static com.longkubi.qlns.common.Global.getTokenFromHeaders;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/salarys")
public class RetSalaryController {
    @Autowired
    private ISalaryService sv;

    @GetMapping()
    public ResponseData<List<SalaryDto>> getAll() {
        return sv.getAll();
    }

    @PostMapping("searchByDto")
    public ResponseData<Page<SalaryDto>> searchByDto(@RequestBody SalarySearchDto searchDto) {
        return sv.searchByDto(searchDto);
    }

    @PostMapping()
    public ResponseData<SalaryDto> create(@Valid @RequestBody SalaryDto languageDto, @RequestHeader HttpHeaders headers) {
        return sv.create(languageDto, getTokenFromHeaders(headers));
    }

    @PutMapping("/{id}")
    public ResponseData<SalaryDto> update(@Valid @RequestBody SalaryDto languageDto, @PathVariable(name = "id") UUID id, @RequestHeader HttpHeaders headers) {
        return sv.update(languageDto, id, getTokenFromHeaders(headers));
    }

    @DeleteMapping("/{id}")
    public ResponseData<Boolean> deleteById(@PathVariable(name = "id") UUID id, @RequestHeader HttpHeaders headers) {
        return sv.deleteById(id, getTokenFromHeaders(headers));
    }
}
