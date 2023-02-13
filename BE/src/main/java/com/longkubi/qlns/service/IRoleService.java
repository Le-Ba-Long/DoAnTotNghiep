package com.longkubi.qlns.service;


import com.longkubi.qlns.model.dto.RoleDto;
import com.longkubi.qlns.model.entity.Role;
import com.longkubi.qlns.model.entity.RoleName;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRoleService {
    List<RoleDto> getAll();

    RoleDto insert(RoleDto role);

    RoleDto update(UUID id, RoleDto role);

    boolean deleteById(UUID id);

    Optional<Role> findByRoleName(RoleName roleName);
}
