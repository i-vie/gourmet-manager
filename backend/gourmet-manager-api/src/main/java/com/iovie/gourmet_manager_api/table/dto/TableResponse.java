package com.iovie.gourmet_manager_api.table.dto;

import com.iovie.gourmet_manager_api.table.TableStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TableResponse {
    Long id;
    Integer number;
    Integer capacity;
    TableStatus status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
