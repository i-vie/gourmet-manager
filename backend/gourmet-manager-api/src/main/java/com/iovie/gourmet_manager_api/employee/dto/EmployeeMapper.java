package com.iovie.gourmet_manager_api.employee.dto;

import com.iovie.gourmet_manager_api.employee.Employee;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "email", source = "employee.user.email")
    EmployeeResponse toEmployeeResponse(Employee employee);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "statusChangedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Employee toEmployee(EmployeeCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "statusChangedAt", ignore = true)
    @Mapping(target = "employeeNumber", ignore = true)
    @Mapping(target = "hireDate", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEmployee(EmployeeUpdateRequest request, @MappingTarget Employee employee);

}
