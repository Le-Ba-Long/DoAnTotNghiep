package com.longkubi.qlns.rest;

import com.longkubi.qlns.model.dto.CommendationAndDisciplineDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.CommendationAndDisciplineSearchDto;
import com.longkubi.qlns.service.ICommendationAndDisciplineService;
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
@RequestMapping("api/commendation-and-disciplines")
public class RetCommendationAndDisciplineController {
    @Autowired
    private ICommendationAndDisciplineService sv;


    @GetMapping()
    public ResponseData<List<CommendationAndDisciplineDto>> getAll() {
        return sv.getAll();
    }

    @PostMapping("searchByDto")
    public ResponseData<Page<CommendationAndDisciplineDto>> searchByDto(@RequestBody CommendationAndDisciplineSearchDto searchDto) {
        return sv.searchByDto(searchDto);
    }

    @PostMapping()
    public ResponseData<CommendationAndDisciplineDto> create(@Valid @RequestBody CommendationAndDisciplineDto commendationAndDisciplineDto, @RequestHeader HttpHeaders headers) {
        return sv.create(commendationAndDisciplineDto, getTokenFromHeaders(headers));
    }

    @PutMapping("/{id}")
    public ResponseData<CommendationAndDisciplineDto> update(@Valid @RequestBody CommendationAndDisciplineDto commendationAndDisciplineDto, @PathVariable(name = "id") UUID id, @RequestHeader HttpHeaders headers) {
        return sv.update(commendationAndDisciplineDto, id, getTokenFromHeaders(headers));
    }

    @DeleteMapping("/{id}")
    public ResponseData<Boolean> deleteById(@PathVariable(name = "id") UUID id) {
        return sv.deleteById(id);
    }
}
