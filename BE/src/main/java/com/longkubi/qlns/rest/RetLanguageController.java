package com.longkubi.qlns.rest;

import com.longkubi.qlns.model.dto.LanguageDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.LanguageSearchDto;
import com.longkubi.qlns.service.ILanguageService;
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
@RequestMapping("api/languages")

public class RetLanguageController {
    @Autowired
    private ILanguageService sv;

    @GetMapping()
    public ResponseData<List<LanguageDto>> getAll() {
        return sv.getAll();
    }

    @PostMapping("searchByDto")
    public ResponseData<Page<LanguageDto>> searchByDto(@RequestBody LanguageSearchDto searchDto) {
        return sv.searchByDto(searchDto);
    }

    @PostMapping()
    public ResponseData<LanguageDto> create(@Valid @RequestBody LanguageDto languageDto, @RequestHeader HttpHeaders headers) {
        return sv.create(languageDto, getTokenFromHeaders(headers));
    }

    @PutMapping("/{id}")
    public ResponseData<LanguageDto> update(@Valid @RequestBody LanguageDto languageDto, @PathVariable(name = "id") UUID id, @RequestHeader HttpHeaders headers) {
        return sv.update(languageDto, id, getTokenFromHeaders(headers));
    }

    @DeleteMapping("/{id}")
    public ResponseData<Boolean> deleteById(@PathVariable(name = "id") UUID id, @RequestHeader HttpHeaders headers) {
        return sv.deleteById(id, getTokenFromHeaders(headers));
    }
}
