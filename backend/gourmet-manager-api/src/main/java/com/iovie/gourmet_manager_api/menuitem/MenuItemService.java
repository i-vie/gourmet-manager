package com.iovie.gourmet_manager_api.menuitem;

import com.iovie.gourmet_manager_api.category.Category;
import com.iovie.gourmet_manager_api.category.CategoryRepository;
import com.iovie.gourmet_manager_api.menuitem.dto.MenuItemCreateRequest;
import com.iovie.gourmet_manager_api.menuitem.dto.MenuItemMapper;
import com.iovie.gourmet_manager_api.menuitem.dto.MenuItemResponse;
import com.iovie.gourmet_manager_api.menuitem.dto.MenuItemUpdateRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final MenuItemMapper menuItemMapper;
    private final CategoryRepository categoryRepository;

    public MenuItemResponse getById(Long id) {
        return menuItemMapper.toResponse(menuItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Menu item not found")));
    }

    public List<MenuItemResponse> getAll() {
        return menuItemRepository.findAll().stream()
                .map(menuItemMapper::toResponse).toList();
    }

    public List<MenuItemResponse> getAllAvailable() {
        return menuItemRepository.findByStatus(MenuItemStatus.AVAILABLE).stream()
                .map(menuItemMapper::toResponse).toList();
    }

    public List<MenuItemResponse> getByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return menuItemRepository.findByCategory(category).stream()
                .map(menuItemMapper::toResponse).toList();
    }

    public List<MenuItemResponse> getAvailableByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return menuItemRepository.findByCategoryAndStatus(category, MenuItemStatus.AVAILABLE).stream()
                .map(menuItemMapper::toResponse).toList();
    }

    public MenuItemResponse create(MenuItemCreateRequest menuItemCreateRequest) {
        Category category = categoryRepository.findById(menuItemCreateRequest.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        MenuItem item = menuItemMapper.toMenuItem(menuItemCreateRequest);
        item.setCategory(category);
        return menuItemMapper
                .toResponse(menuItemRepository.save(item));
    }

    public MenuItemResponse update(Long id, MenuItemUpdateRequest menuItemUpdateRequest) {
        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Menu item not found"));

        if (menuItemUpdateRequest.getCategoryId() != null) {
            Category category = categoryRepository.findById(menuItemUpdateRequest.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));
            item.setCategory(category);
        }
        menuItemMapper.updateMenuItem(menuItemUpdateRequest, item);
        return menuItemMapper.toResponse(menuItemRepository.save(item));
    }

    public MenuItemResponse updateStatus(Long id, MenuItemStatus status) {
        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Menu item not found"));
        item.setStatus(status);
        return menuItemMapper.toResponse(menuItemRepository.save(item));
    }

}
