package com.iovie.gourmet_manager_api.menuitem;

import com.iovie.gourmet_manager_api.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByStatus(MenuItemStatus status);
    List<MenuItem> findByCategory(Category category);
    List<MenuItem> findByCategoryAndStatus(Category category, MenuItemStatus status);
    Boolean existsByNameAndCategory(String name, Category category);
}