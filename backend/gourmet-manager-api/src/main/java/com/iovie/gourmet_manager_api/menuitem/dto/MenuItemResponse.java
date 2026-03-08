package com.iovie.gourmet_manager_api.menuitem.dto;

import com.iovie.gourmet_manager_api.category.dto.CategoryResponse;
import com.iovie.gourmet_manager_api.menuitem.MenuItemStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MenuItemResponse {
    Long id;
    String name;
    String description;
    BigDecimal price;
    String imageUrl;
    MenuItemStatus status;
    CategoryResponse category;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
