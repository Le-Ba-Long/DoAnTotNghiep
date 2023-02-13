package com.longkubi.qlns.rest;

import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.UserDto;
import com.longkubi.qlns.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/users")
public class RestUserController {

    private final IUserService iUserService;

    public RestUserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @GetMapping()
    public ResponseData<List<UserDto>> getAll() {
        return iUserService.getAll();
    }

    @PostMapping()
    public ResponseData<?> insert(@Valid @RequestBody UserDto userDto) {
        return iUserService.insert(userDto);
    }

    @PutMapping("/{id}")
    public ResponseData<?> update(@Valid @RequestBody UserDto userDto, @PathVariable(name = "id") UUID id) {
        return iUserService.update(id, userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseData<?> deleteById(@PathVariable(name = "id") UUID id) {
        return iUserService.deleteById(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> checkUserNameAndPassWord(@RequestParam(name = "userName") String userName,
                                                      @RequestParam(name = "passWord") String passWord) {
        return new ResponseEntity<>(iUserService.checkUserNameAndPassWord(userName, passWord), HttpStatus.OK);
    }
}
