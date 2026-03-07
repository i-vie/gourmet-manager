package com.iovie.gourmet_manager_api.category;

import com.iovie.gourmet_manager_api.category.dto.CategoryRequest;
import com.iovie.gourmet_manager_api.category.dto.CategoryResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream()
                .map(this::toResponse).toList();
    }

    public List<CategoryResponse> getAllActive() {
        return categoryRepository.findByActiveTrue().stream()
                .map(this::toResponse).toList();
    }

    public CategoryResponse getById(Long id){
        return toResponse(categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found")));
    }

    public CategoryResponse create(CategoryRequest categoryRequest) {
        if (categoryRepository.existsByName(categoryRequest.name())) {
            throw new IllegalArgumentException("Category name already exists");
        }
        Category category = new Category();
        category.setName(categoryRequest.name());
        return toResponse(categoryRepository.save(category));
    }

    public CategoryResponse update(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        if (!category.getName().equals(categoryRequest.name()) && categoryRepository.existsByName(categoryRequest.name())) {
            throw new IllegalArgumentException("Category name already in use");
        }
        category.setName(categoryRequest.name());
        return toResponse(categoryRepository.save(category));
    }

    public CategoryResponse activate(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        category.setActive(true);
        return toResponse(categoryRepository.save(category));
    }

    public CategoryResponse deactivate(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        category.setActive(false);
        return toResponse(categoryRepository.save(category));
    }

    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getActive(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}
