package com.iovie.gourmet_manager_api.employee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {

    Optional<Position> findByName(String Name);
    Boolean existsByName(String name);
    List<Position> findByActiveTrue();
}
