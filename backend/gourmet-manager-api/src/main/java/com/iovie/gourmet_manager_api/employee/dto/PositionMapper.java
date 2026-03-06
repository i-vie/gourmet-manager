package com.iovie.gourmet_manager_api.employee.dto;

import com.iovie.gourmet_manager_api.employee.Position;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PositionMapper {

    PositionResponse toPositionResponse(Position position);

    Position toPosition(PositionCreateRequest request);
}

