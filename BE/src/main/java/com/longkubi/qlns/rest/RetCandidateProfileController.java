package com.longkubi.qlns.rest;

import com.longkubi.qlns.model.dto.CandidateProfileDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.CandidateProfileSearchDto;
import com.longkubi.qlns.service.ICandidateProfileService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.longkubi.qlns.common.Global.getTokenFromHeaders;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/candidate-profiles")
//@PreAuthorize("hasAnyAuthority('ADMIN','ACCOUNTANCY')")
@Slf4j
public class RetCandidateProfileController {
    @Autowired
    private ICandidateProfileService sv;

    @Cacheable(value = "candidateProfiles")
    @GetMapping()
    public ResponseData<List<CandidateProfileDto>> getAll() {
        long startTime = System.currentTimeMillis();
        log.info(String.valueOf(startTime));
        ResponseData<List<CandidateProfileDto> >list = sv.getAll();
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        log.info( "Tổng Thời gian call : "+String.valueOf(elapsedTime));
        return list;
    }

    @PostMapping("searchByDto")
    public ResponseData<Page<CandidateProfileDto>> searchByDto(@RequestBody CandidateProfileSearchDto searchDto) {
        return sv.searchByDto(searchDto);
    }


    @PostMapping()
    public ResponseData<CandidateProfileDto> create(@Valid @RequestBody CandidateProfileDto candidateProfileDto, @RequestHeader HttpHeaders headers) {
        return sv.create(candidateProfileDto, getTokenFromHeaders(headers));
    }

    @PutMapping("/{id}")
    public ResponseData<CandidateProfileDto> update(@Valid @RequestBody CandidateProfileDto positionDto, @PathVariable(name = "id") UUID id, @RequestHeader HttpHeaders headers) {
        return sv.update(positionDto, id, getTokenFromHeaders(headers));
    }

    @DeleteMapping("/{id}")
    public ResponseData<Boolean> deleteById(@PathVariable(name = "id") UUID id, @RequestHeader HttpHeaders headers) {
        return sv.deleteById(id);
    }
}
