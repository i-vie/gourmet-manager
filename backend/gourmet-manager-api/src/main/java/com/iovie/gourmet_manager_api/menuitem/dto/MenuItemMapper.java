package com.iovie.gourmet_manager_api.menuitem.dto;

import com.iovie.gourmet_manager_api.category.dto.CategoryMapper;
import com.iovie.gourmet_manager_api.menuitem.MenuItem;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface MenuItemMapper {

    MenuItemResponse toResponse(MenuItem menuItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    MenuItem toMenuItem(MenuItemCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateMenuItem(MenuItemUpdateRequest request, @MappingTarget MenuItem menuItem);
}