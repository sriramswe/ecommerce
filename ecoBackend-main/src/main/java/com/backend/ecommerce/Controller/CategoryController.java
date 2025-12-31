package com.backend.ecommerce.Controller;

import com.backend.ecommerce.Payload.DTO.CategoryDto;
import com.backend.ecommerce.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // =================================================
    // 🟢 PUBLIC APIs (USER / SELLER / ADMIN)
    // =================================================

    // Get all categories (flat list)
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    // Get category by ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    // Get category by name
    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryDto> getCategoryByName(@PathVariable String name) {
        return ResponseEntity.ok(categoryService.getCategoryByName(name));
    }

    // Get category tree (nested)
    @GetMapping("/tree")
    public ResponseEntity<List<CategoryDto>> getCategoryTree() {
        return ResponseEntity.ok(categoryService.getCategoryTree());
    }

    // =================================================
    // 🔴 ADMIN ONLY APIs
    // =================================================

    // Create category
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(
            @RequestParam String name,
            @RequestParam(required = false) Long parentId
    ) {
        CategoryDto parentCategory = parentId != null
                ? CategoryDto.builder().id(parentId).build()
                : null;

        return ResponseEntity.ok(
                categoryService.createCategory(name, parentCategory)
        );
    }

    // Update category
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam(required = false) Long parentId
    ) {
        CategoryDto parentCategory = parentId != null
                ? CategoryDto.builder().id(parentId).build()
                : null;

        return ResponseEntity.ok(
                categoryService.updateCategory(id, name, parentCategory)
        );
    }

    // Delete category
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully");
    }
}
