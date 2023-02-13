package com.longkubi.qlns.rest;

import com.longkubi.qlns.model.dto.CertificateDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.CertificateSearchDto;
import com.longkubi.qlns.service.ICertificateService;
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
@RequestMapping("api/certificates")

public class RetCertificateController {
    @Autowired
    private ICertificateService sv;
    @GetMapping()
    public ResponseData<List<CertificateDto>> getAll() {
        return sv.getAll();
    }

    @PostMapping("searchByDto")
    public ResponseData<Page<CertificateDto>> searchByDto(@RequestBody CertificateSearchDto searchDto) {
        return sv.searchByDto(searchDto);
    }

    @PostMapping()
    public ResponseData<CertificateDto> create(@Valid @RequestBody CertificateDto certificateDto, @RequestHeader HttpHeaders headers) {
        return sv.create(certificateDto, getTokenFromHeaders(headers));
    }

    @PutMapping("/{id}")
    public ResponseData<CertificateDto> update(@Valid @RequestBody CertificateDto certificateDto, @PathVariable(name = "id") UUID id, @RequestHeader HttpHeaders headers) {
        return sv.update(certificateDto, id, getTokenFromHeaders(headers));
    }

    @DeleteMapping("/{id}")
    public ResponseData<Boolean> deleteById(@PathVariable(name = "id") UUID id, @RequestHeader HttpHeaders headers) {
        return sv.deleteById(id);
    }
}
