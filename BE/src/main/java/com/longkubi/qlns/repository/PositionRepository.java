package com.longkubi.qlns.repository;

import com.longkubi.qlns.model.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface PositionRepository extends JpaRepository<Position, UUID> {
    Boolean existsPositionById(UUID id);

    Boolean existsPositionByCode(String code);

    Boolean existsPositionByName(String name);

    @Query("select count (e) from Position e where e.code = :code and e.id<>:id")
    Integer exclusionCode(@Param("code") String code, UUID id);

    @Query("select count (e) from Position e where e.name = :name and e.id<>:id")
    Integer exclusionName(@Param("name") String name, UUID id);

    Position getPositionById(UUID id);

    @Query("select e from Position e where e.id in :ids")
    Set<Position> getPositionByListId(@Param("ids") Set<UUID> ids);

    @Query("select e from Position e ORDER BY e.dateCreated ASC  ")
    List<Position> getAll();
}
