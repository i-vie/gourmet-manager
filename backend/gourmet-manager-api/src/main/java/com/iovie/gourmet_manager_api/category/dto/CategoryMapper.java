package com.iovie.gourmet_manager_api.category.dto;

import com.iovie.gourmet_manager_api.category.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toResponse(Category category);
}
