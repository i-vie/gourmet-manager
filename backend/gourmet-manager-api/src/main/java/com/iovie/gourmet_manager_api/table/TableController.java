package com.iovie.gourmet_manager_api.table;

import com.iovie.gourmet_manager_api.table.dto.TableCreateRequest;
import com.iovie.gourmet_manager_api.table.dto.TableMapper;
import com.iovie.gourmet_manager_api.table.dto.TableResponse;
import com.iovie.gourmet_manager_api.table.dto.TableUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/tables")
public class TableController {

    private final TableService tableService;
    private final TableMapper tableMapper;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'WAITER', 'KITCHEN')")
    public ResponseEntity<TableResponse> getTableById(@PathVariable Long id) {
        return ResponseEntity.ok(tableMapper.toTableResponse(tableService.getById(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<TableResponse>> getAllTables() {
        List<RestaurantTable> tables = tableService.getAll();
        return ResponseEntity.ok(
                tables.stream().map(
                        tableMapper::toTableResponse
                ).toList()
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<TableResponse> createTable(@RequestBody @Valid TableCreateRequest tableCreateRequest) {
        RestaurantTable table = tableService.create(tableCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(tableMapper.toTableResponse(table));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<TableResponse> updateTable(@PathVariable Long id, @RequestBody @Valid TableUpdateRequest request) {
        return ResponseEntity.ok(tableMapper.toTableResponse(tableService.update(id, request)));
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<TableResponse> activateTable(@PathVariable Long id) {
        return ResponseEntity.ok(tableMapper.toTableResponse(tableService.updateStatus(id, TableStatus.ACTIVE)));
    }

    @PatchMapping("/{id}/inactivate")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<TableResponse> makeUnavailableTable(@PathVariable Long id) {
        return ResponseEntity.ok(tableMapper.toTableResponse(tableService.updateStatus(id, TableStatus.UNAVAILABLE)));
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<TableResponse> deactivateTable(@PathVariable Long id) {
        return ResponseEntity.ok(tableMapper.toTableResponse(tableService.updateStatus(id, TableStatus.DEACTIVATED)));
    }
}
