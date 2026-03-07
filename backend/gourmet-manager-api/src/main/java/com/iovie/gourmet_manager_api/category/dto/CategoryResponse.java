package com.iovie.gourmet_manager_api.category.dto;

import java.time.LocalDateTime;

public record CategoryResponse(
        Long id,
        String name,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}