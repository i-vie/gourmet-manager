package com.iovie.gourmet_manager_api.employee.dto;

import com.iovie.gourmet_manager_api.employee.EmploymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

    Long id;
    String employeeNumber;
    String name;
    String phone;
    LocalDate hireDate;
    EmploymentStatus status;
    LocalDateTime statusChangedAt;
    PositionResponse position;
    String email;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

}
