package com.iovie.gourmet_manager_api.employee.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreateRequest {

    @NotBlank String name;
    String phone;
    LocalDate hireDate;
    Long positionId;
    Long userId;

}
