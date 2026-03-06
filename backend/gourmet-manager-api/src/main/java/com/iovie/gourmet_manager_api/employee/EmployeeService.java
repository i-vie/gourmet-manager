package com.iovie.gourmet_manager_api.employee;

import com.iovie.gourmet_manager_api.employee.dto.EmployeeCreateRequest;
import com.iovie.gourmet_manager_api.employee.dto.EmployeeMapper;
import com.iovie.gourmet_manager_api.employee.dto.EmployeeUpdateRequest;
import com.iovie.gourmet_manager_api.user.User;
import com.iovie.gourmet_manager_api.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final PositionRepository positionRepository;
    private final EmployeeMapper employeeMapper;

    public Employee getById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        return employee;
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public List<Employee> getAllActive() {
        return employeeRepository.findByStatus(EmploymentStatus.ACTIVE);
    }

    public Employee create(EmployeeCreateRequest employeeCreateRequest) {
        User user = userRepository.findById(employeeCreateRequest.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (employeeRepository.findByUser(user).isPresent()) {
            throw new IllegalArgumentException("User already has an employee profile");
        }

        Position position = positionRepository.findById(employeeCreateRequest.getPositionId())
                .orElseThrow(() -> new EntityNotFoundException("Position not found"));

        Employee employee = employeeMapper.toEmployee(employeeCreateRequest);
        employee.setEmployeeNumber(generateEmployeeNumber());
        employee.setUser(user);
        employee.setPosition(position);

        return employeeRepository.save(employee);
    }

    public Employee update(Long id, EmployeeUpdateRequest employeeUpdateRequest) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        employeeMapper.updateEmployee(employeeUpdateRequest, employee);
        if (employeeUpdateRequest.getPositionId() != null) {
            Position position = positionRepository.findById(employeeUpdateRequest.getPositionId())
                    .orElseThrow(() -> new EntityNotFoundException("Position not found"));
            employee.setPosition(position);
        }
        return employeeRepository.save(employee);
    }

    public void updateStatus(Long id, EmploymentStatus employmentStatus) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        employee.setStatus(employmentStatus);
        User user = employee.getUser();
        switch (employmentStatus) {
            case ACTIVE -> user.setEnabled(true);
            case SUSPENDED, TERMINATED -> user.setEnabled(false);
            case ON_LEAVE -> {}
        }
        employee.setStatusChangedAt(LocalDateTime.now());
        userRepository.save(user);
        employeeRepository.save(employee);
    }

    public Employee getByUser(User user) {
        return employeeRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
    }

    private String generateEmployeeNumber() {
        long count = employeeRepository.count() + 1;
        return String.format("EMP%03d", count);
    }
}
