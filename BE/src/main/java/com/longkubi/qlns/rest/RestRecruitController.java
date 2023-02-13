package com.longkubi.qlns.rest;

import com.longkubi.qlns.model.dto.RecruitDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.RecruitSearchDto;
import com.longkubi.qlns.service.IRecruitService;
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
@RequestMapping("api/recruits")

public class RestRecruitController {
    @Autowired
    private IRecruitService sv;

    @GetMapping()
    public ResponseData<List<RecruitDto>> getAll() {
        return sv.getAll();
    }

    @PostMapping("searchByDto")
    public ResponseData<Page<RecruitDto>> searchByDto(@RequestBody RecruitSearchDto searchDto) {
        return sv.searchByDto(searchDto);
    }

    @GetMapping("/get-all-approval-recruit")
    public ResponseData<List<RecruitDto>> getAllApprovalRecruit() {
        return  sv.getAllApprovalRecruit();
    }

    @PostMapping()
    public ResponseData<RecruitDto> create(@Valid @RequestBody RecruitDto recruitDto, @RequestHeader HttpHeaders headers) {
        return sv.create(recruitDto, getTokenFromHeaders(headers));
    }

    @PutMapping("/{id}")
    public ResponseData<RecruitDto> update(@Valid @RequestBody RecruitDto recruitDto, @PathVariable(name = "id") UUID id, @RequestHeader HttpHeaders headers) {
        return sv.update(recruitDto, id, getTokenFromHeaders(headers));
    }

    @DeleteMapping("/{id}")
    public ResponseData<Boolean> deleteById(@PathVariable(name = "id") UUID id) {
        return sv.deleteById(id);
    }
}
