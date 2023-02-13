package com.longkubi.qlns.repository;

import com.longkubi.qlns.model.entity.Role;
import com.longkubi.qlns.model.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByRoleName(RoleName roleName);
    boolean existsByRoleName(RoleName roleName);
    boolean existsById(UUID id);

}
