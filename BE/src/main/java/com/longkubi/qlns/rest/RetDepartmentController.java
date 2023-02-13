package com.longkubi.qlns.rest;

import com.longkubi.qlns.model.dto.DepartmentDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.DepartmentSearchDto;
import com.longkubi.qlns.service.IDepartmentService;
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
@RequestMapping("api/departments")

public class RetDepartmentController {
    @Autowired
    private IDepartmentService sv;

    @GetMapping()
    public ResponseData<List<DepartmentDto>> getAll() {
        return sv.getAll();
    }

    @PostMapping("searchByDto")
    public ResponseData<Page<DepartmentDto>> searchByDto(@RequestBody DepartmentSearchDto searchDto) {
        return sv.searchByDto(searchDto);
    }

    @PostMapping()
    public ResponseData<DepartmentDto> create(@Valid @RequestBody DepartmentDto departmentDto, @RequestHeader HttpHeaders headers) {
        return sv.create(departmentDto, getTokenFromHeaders(headers));
    }

    @PutMapping("/{id}")
    public ResponseData<DepartmentDto> update(@Valid @RequestBody DepartmentDto departmentDto, @PathVariable(name = "id") UUID id, @RequestHeader HttpHeaders headers) {
        return sv.update(departmentDto, id, getTokenFromHeaders(headers));
    }

    @DeleteMapping("/{id}")
    public ResponseData<?> deleteById(@PathVariable(name = "id") UUID id, @RequestHeader HttpHeaders headers) {
            return sv.deleteById(id, getTokenFromHeaders(headers));

    }
}
