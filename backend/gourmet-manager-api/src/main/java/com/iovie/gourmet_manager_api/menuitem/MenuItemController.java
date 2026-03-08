package com.iovie.gourmet_manager_api.menuitem;

import com.iovie.gourmet_manager_api.menuitem.dto.MenuItemCreateRequest;
import com.iovie.gourmet_manager_api.menuitem.dto.MenuItemResponse;
import com.iovie.gourmet_manager_api.menuitem.dto.MenuItemUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/menu-items")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<MenuItemResponse>> getAllMenuItems() {
        return ResponseEntity.ok(menuItemService.getAll());
    }

    @GetMapping("/available")
    public ResponseEntity<List<MenuItemResponse>> getAllAvailableMenuItems() {
        return ResponseEntity.ok(menuItemService.getAllAvailable());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponse> getMenuItemById(@PathVariable Long id) {
        return ResponseEntity.ok(menuItemService.getById(id));
    }

    @GetMapping("/category/{categoryId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<MenuItemResponse>> getMenuItemsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(menuItemService.getByCategory(categoryId));
    }

    @GetMapping("/category/{categoryId}/available")
    public ResponseEntity<List<MenuItemResponse>> getMenuItemsAvailableByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(menuItemService.getAvailableByCategory(categoryId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<MenuItemResponse> createMenuItem(@RequestBody @Valid MenuItemCreateRequest menuItemCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(menuItemService.create(menuItemCreateRequest));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<MenuItemResponse> updateMenuItem(@PathVariable Long id, @RequestBody @Valid MenuItemUpdateRequest menuItemUpdateRequest) {
        return ResponseEntity.ok(menuItemService.update(id, menuItemUpdateRequest));
    }

    @PatchMapping("/{id}/available")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<MenuItemResponse> markAvailableMenuItem(@PathVariable Long id) {
        return ResponseEntity.ok(menuItemService.updateStatus(id, MenuItemStatus.AVAILABLE));
    }

    @PatchMapping("/{id}/unavailable")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<MenuItemResponse> markUnavailableMenuItem(@PathVariable Long id) {
        return ResponseEntity.ok(menuItemService.updateStatus(id, MenuItemStatus.UNAVAILABLE));
    }

}
