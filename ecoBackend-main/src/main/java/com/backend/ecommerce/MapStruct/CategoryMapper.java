package com.backend.ecommerce.MapStruct;

import com.backend.ecommerce.Model.Category;
import com.backend.ecommerce.Model.Product;
import com.backend.ecommerce.Payload.DTO.CategoryDto;
import com.backend.ecommerce.Payload.DTO.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public static CategoryDto toDto(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setParentCategory(category.getParentCategory());
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setLevel(category.getLevel());
        return categoryDto;
    }
    public static  Category toEntity(CategoryDto categoryDto){
        Category category = new Category();
        category.setParentCategory(categoryDto.getParentCategory());
        category.setName(category.getName());
        category.setLevel(category.getLevel());
        category.setId(category.getId());
        return category;
    }
}
