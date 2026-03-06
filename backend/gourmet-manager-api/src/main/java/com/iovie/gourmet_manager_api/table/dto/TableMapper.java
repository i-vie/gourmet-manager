package com.iovie.gourmet_manager_api.table.dto;

import com.iovie.gourmet_manager_api.table.RestaurantTable;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TableMapper {

    TableResponse toTableResponse(RestaurantTable table);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    RestaurantTable toTable(TableCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateTable(TableUpdateRequest request, @MappingTarget RestaurantTable table);
}
