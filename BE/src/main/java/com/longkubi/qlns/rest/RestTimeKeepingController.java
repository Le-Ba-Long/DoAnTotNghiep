package com.longkubi.qlns.rest;

import com.longkubi.qlns.model.dto.EmployeeDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.TimeKeepingDto;
import com.longkubi.qlns.model.dto.search.EmployeeSearchDto;
import com.longkubi.qlns.model.dto.search.TimeKeepingSearchDto;
import com.longkubi.qlns.service.ITimeKeepingService;
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
@RequestMapping("api/time-keepings")
public class RestTimeKeepingController {

    @Autowired
    private ITimeKeepingService sv;

    @GetMapping()
    public ResponseData<List<TimeKeepingDto>> getAll() {
        return sv.getAll();
    }

    @PostMapping()
    public ResponseData<TimeKeepingDto> create(@Valid @RequestBody TimeKeepingDto timeKeepingDto, @RequestHeader HttpHeaders headers) {
        return sv.create(timeKeepingDto, getTokenFromHeaders(headers));
    }
    @PutMapping("/{id}")
    public ResponseData<TimeKeepingDto> update(@Valid @RequestBody TimeKeepingDto timeKeepingDto,@PathVariable("id") UUID id, @RequestHeader HttpHeaders headers) {
        return sv.update(timeKeepingDto,id, getTokenFromHeaders(headers));
    }
    @DeleteMapping ("/{id}")
    public ResponseData<Boolean> deleteById(@PathVariable("id") UUID id) {
        return sv.deleteById(id);
    }

    @PostMapping("/searchByDto")
    public ResponseData<Page<TimeKeepingDto>> searchByDto(@RequestBody TimeKeepingSearchDto searchDto) {
        return sv.searchByDto(searchDto);
    }


}
