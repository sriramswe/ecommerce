package com.backend.ecommerce.Service;
import com.backend.ecommerce.Model.Category;
import com.backend.ecommerce.Payload.DTO.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(String name, CategoryDto parentCategory);

    CategoryDto updateCategory(Long id, String name, CategoryDto parentCategory);
    CategoryDto getCategoryById(Long id);

    CategoryDto getCategoryByName(String name);

    List<CategoryDto> getAllCategories();

    void deleteCategory(Long id);
    public List<CategoryDto> getCategoryTree();
}
