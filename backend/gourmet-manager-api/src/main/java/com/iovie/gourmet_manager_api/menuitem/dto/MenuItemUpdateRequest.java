package com.iovie.gourmet_manager_api.menuitem.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemUpdateRequest {
    String name;
    String description;
    @Positive
    BigDecimal price;
    String imageUrl;
    Long categoryId;
}
