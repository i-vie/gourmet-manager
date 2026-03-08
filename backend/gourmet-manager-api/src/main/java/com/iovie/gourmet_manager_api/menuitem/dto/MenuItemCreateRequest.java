package com.iovie.gourmet_manager_api.menuitem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class MenuItemCreateRequest {
    @NotBlank
    String name;
    @NotBlank String description;
    @NotNull
    @Positive
    BigDecimal price;
    String imageUrl;
    @NotNull Long categoryId;
}
