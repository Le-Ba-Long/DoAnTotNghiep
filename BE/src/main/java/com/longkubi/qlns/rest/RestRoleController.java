package com.longkubi.qlns.rest;

import com.longkubi.qlns.model.dto.RoleDto;
import com.longkubi.qlns.service.IRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/roles")
public class RestRoleController {

    private final IRoleService iRoleService;

    public RestRoleController(IRoleService iRoleService) {
        this.iRoleService = iRoleService;
    }

    @GetMapping()
    public ResponseEntity<List<RoleDto>> getAll() {
        return new ResponseEntity<>(iRoleService.getAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<RoleDto> insert(@Valid @RequestBody RoleDto roleDto) {
        return new ResponseEntity<>(iRoleService.insert(roleDto), HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> update(@Valid @RequestBody RoleDto roleDto, @PathVariable(name = "id") UUID id) {
        return new ResponseEntity<>(iRoleService.update(id, roleDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable(name = "id") UUID id) {
        return new ResponseEntity<>(iRoleService.deleteById(id), HttpStatus.OK);
    }

    @GetMapping("/hello")
    public String xinchao() {
        return "xinchao";
    }

}
