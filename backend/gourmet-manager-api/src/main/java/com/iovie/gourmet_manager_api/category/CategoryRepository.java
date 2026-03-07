package com.iovie.gourmet_manager_api.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByActiveTrue();
    Boolean existsByName(String name);
}
