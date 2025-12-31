package com.backend.ecommerce.Service;

import com.backend.ecommerce.MapStruct.CategoryMapper;
import com.backend.ecommerce.Model.Category;
import com.backend.ecommerce.Payload.DTO.CategoryDto;
import com.backend.ecommerce.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    // ================= CREATE CATEGORY =================
    @Override
    public CategoryDto createCategory(String name, CategoryDto parentCategoryDto) {

        Category parentCategory = null;

        if (parentCategoryDto != null && parentCategoryDto.getId() != null) {
            parentCategory = categoryRepository.findById(parentCategoryDto.getId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
        }

        // Prevent duplicate category under same parent
        Category existing;
        if (parentCategory == null) {
            existing = categoryRepository.findByName(name);
        } else {
            existing = categoryRepository.findByNameAndParentCategory(name, parentCategory);
        }

        if (existing != null) {
            throw new RuntimeException("Category already exists");
        }

        Category category = new Category();
        category.setName(name);
        category.setParentCategory(parentCategory);
        category.setLevel(parentCategory == null ? 1 : parentCategory.getLevel() + 1);
        assert parentCategoryDto != null;
        category.setCategory_img(parentCategoryDto.getCategory_img());
        Category saved = categoryRepository.save(category);
        return categoryMapper.toDto(saved);
    }

    // ================= UPDATE CATEGORY =================
    @Override
    public CategoryDto updateCategory(Long id, String name, CategoryDto parentCategoryDto) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Category parentCategory = null;
        if (parentCategoryDto != null && parentCategoryDto.getId() != null) {
            parentCategory = categoryRepository.findById(parentCategoryDto.getId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
        }

        category.setName(name);
        category.setParentCategory(parentCategory);
        category.setLevel(parentCategory == null ? 1 : parentCategory.getLevel() + 1);

        return categoryMapper.toDto(categoryRepository.save(category));
    }

    // ================= GET CATEGORY BY ID =================
    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return categoryMapper.toDto(category);
    }

    // ================= GET CATEGORY BY NAME =================
    @Override
    public CategoryDto getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name);
        if (category == null) {
            throw new RuntimeException("Category not found");
        }
        return categoryMapper.toDto(category);
    }

    // ================= GET ALL CATEGORIES =================
    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    // ================= DELETE CATEGORY =================
    @Override
    public void deleteCategory(Long id) {

        if (categoryRepository.existsByParentCategory_Id(id)) {
            throw new RuntimeException("Cannot delete category with subcategories");
        }

        categoryRepository.deleteById(id);
    }

    // ================= CATEGORY TREE =================
    @Override
    public List<CategoryDto> getCategoryTree() {

        List<Category> rootCategories =
                categoryRepository.findByParentCategoryIsNull();

        return rootCategories.stream()
                .map(this::mapToTreeDto)
                .toList();
    }

    private CategoryDto mapToTreeDto(Category category) {

        List<Category> children =
                categoryRepository.findByParentCategory_Id(category.getId());

        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .level(category.getLevel())
                .parentId(
                        category.getParentCategory() != null
                                ? category.getParentCategory().getId()
                                : null
                )
                .children(
                        children.stream()
                                .map(this::mapToTreeDto)
                                .toList()
                )
                .build();
    }
}
