package com.longkubi.qlns.rest;

import com.longkubi.qlns.model.dto.RecruitDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.service.IRecruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static com.longkubi.qlns.common.Global.getTokenFromHeaders;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/planApprovalAdmin")
public class RestPlanApprovalAdminController {
    @Autowired
    private IRecruitService sv;
    @PutMapping("approval-recruit/{id}")
    public ResponseData<?> approvalRecruit(@Valid @RequestBody RecruitDto recruitDto, @PathVariable(name = "id") UUID id, @RequestHeader HttpHeaders headers) {
        return sv.updateStatus(recruitDto, id, getTokenFromHeaders(headers));
    }

}
