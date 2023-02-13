package com.longkubi.qlns.rest;

import com.longkubi.qlns.model.dto.PositionDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.PositionSearchDto;
import com.longkubi.qlns.service.IPositionService;
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
@RequestMapping("api/positions")

public class RestPositionController {
    @Autowired
    private IPositionService sv;

    @GetMapping()
    public ResponseData<List<PositionDto>> getAll() {
        return sv.getAll();
    }

    @PostMapping("searchByDto")
    public ResponseData<Page<PositionDto>> searchByDto(@RequestBody PositionSearchDto searchDto) {
        return sv.searchByDto(searchDto);
    }

    @PostMapping()
    public ResponseData<PositionDto> create(@Valid @RequestBody PositionDto positionDto, @RequestHeader HttpHeaders headers) {
        return sv.create(positionDto, getTokenFromHeaders(headers));
    }

    @PutMapping("/{id}")
    public ResponseData<PositionDto> update(@Valid @RequestBody PositionDto positionDto, @PathVariable(name = "id") UUID id, @RequestHeader HttpHeaders headers) {
        return sv.update(positionDto, id, getTokenFromHeaders(headers));
    }

    @DeleteMapping("/{id}")
    public ResponseData<Boolean> deleteById(@PathVariable(name = "id") UUID id, @RequestHeader HttpHeaders headers) {
        return sv.deleteById(id, getTokenFromHeaders(headers));
    }
}
