package com.iovie.gourmet_manager_api.employee;

import com.iovie.gourmet_manager_api.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByUser(User user);

    Optional<Employee> findByEmployeeNumber(String employeeNumber);

    Boolean existsByEmployeeNumber(String employeeNumber);

    List<Employee> findByStatus(EmploymentStatus status);

    List<Employee> findByPosition(Position position);

}
