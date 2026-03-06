package com.iovie.gourmet_manager_api.table.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TableUpdateRequest {
    @NotNull
    Integer number;
    @NotNull Integer capacity;
}
