package com.iovie.gourmet_manager_api.employee;

import com.iovie.gourmet_manager_api.employee.dto.EmployeeCreateRequest;
import com.iovie.gourmet_manager_api.employee.dto.EmployeeMapper;
import com.iovie.gourmet_manager_api.employee.dto.EmployeeResponse;
import com.iovie.gourmet_manager_api.employee.dto.EmployeeUpdateRequest;
import com.iovie.gourmet_manager_api.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeMapper employeeMapper;
    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeMapper.toEmployeeResponse(employeeService.getById(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        List<Employee> employees = employeeService.getAll();
        return ResponseEntity.ok(employees.stream()
                .map(employeeMapper::toEmployeeResponse).toList());
    }

    @GetMapping("/me")
    public ResponseEntity<EmployeeResponse> getMyProfile() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee employee = employeeService.getByUser(user);
        return ResponseEntity.ok(employeeMapper.toEmployeeResponse(employee));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody @Valid EmployeeCreateRequest employeeCreateRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeMapper.toEmployeeResponse(employeeService.create(employeeCreateRequest)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeUpdateRequest employeeUpdateRequest) {
        return ResponseEntity
                .ok(employeeMapper.toEmployeeResponse(employeeService.update(id, employeeUpdateRequest)));
    }

    @PatchMapping("/{id}/terminate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> terminateEmployee(@PathVariable Long id) {
        employeeService.updateStatus(id, EmploymentStatus.TERMINATED);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/leave")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> onLeaveEmployee(@PathVariable Long id) {
        employeeService.updateStatus(id, EmploymentStatus.ON_LEAVE);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> activateEmployee(@PathVariable Long id) {
        employeeService.updateStatus(id, EmploymentStatus.ACTIVE);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/suspend")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> suspendEmployee(@PathVariable Long id) {
        employeeService.updateStatus(id, EmploymentStatus.SUSPENDED);
        return ResponseEntity.noContent().build();
    }

}
