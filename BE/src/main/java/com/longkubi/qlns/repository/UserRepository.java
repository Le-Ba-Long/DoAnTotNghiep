package com.longkubi.qlns.repository;


import com.longkubi.qlns.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface
UserRepository extends JpaRepository<User, UUID> {
    boolean existsByUserName(String userName);

    boolean existsById(UUID id);

    boolean existsByEmail(String email);

    Optional<User> findByUserName(String userName);

    @Query("select e from User e ORDER BY e.id ASC  ")
    List<User> getAll();

    boolean existsByUserNameAndAndPassWord(String userName, String passWord);

    User getUserById(UUID uuid);
}
