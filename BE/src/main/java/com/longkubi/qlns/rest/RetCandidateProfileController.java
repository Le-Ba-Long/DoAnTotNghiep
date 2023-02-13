package com.longkubi.qlns.rest;

import com.longkubi.qlns.model.dto.CandidateProfileDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.CandidateProfileSearchDto;
import com.longkubi.qlns.service.ICandidateProfileService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RetCandidateProfileController {
    @Autowired
    private ICandidateProfileService sv;


    @GetMapping()
    public ResponseData<List<CandidateProfileDto>> getAll() {
        return sv.getAll();
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
