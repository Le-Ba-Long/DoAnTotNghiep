package com.longkubi.qlns.service.impl;

import com.longkubi.qlns.model.dto.RoleDto;
import com.longkubi.qlns.model.entity.Role;
import com.longkubi.qlns.model.entity.RoleName;
import com.longkubi.qlns.repository.RoleRepository;
import com.longkubi.qlns.service.IRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Transactional
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<RoleDto> getAll() {
        return roleRepository.findAll().stream().map(role -> modelMapper.map(role, RoleDto.class)).collect(Collectors.toList());
    }

    @Override
    public RoleDto insert(RoleDto roleDto) {
        return modelMapper.map(roleRepository.save(modelMapper.map(roleDto, Role.class)), RoleDto.class);

    }

    @Override
    public RoleDto update(UUID id, RoleDto roleDto) {
        if (roleRepository.existsByRoleName(roleDto.getRoleName()))
            throw new IllegalArgumentException("Tên Tài Khoản Đã Tồn Tại");
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            Role entity = role.get();
            entity.setRoleDescription(roleDto.getRoleDescription());
            entity.setRoleName(roleDto.getRoleName());
            return modelMapper.map(roleRepository.save(entity), RoleDto.class);
        }
        throw new IllegalArgumentException("Update Thất Bại");
    }

    @Override
    public boolean deleteById(UUID id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Role> findByRoleName(RoleName roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
