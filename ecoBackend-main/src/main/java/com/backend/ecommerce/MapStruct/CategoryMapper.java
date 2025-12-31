package com.backend.ecommerce.MapStruct;

import com.backend.ecommerce.Model.Category;
import com.backend.ecommerce.Payload.DTO.CategoryDto;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    // ========= ENTITY → DTO =========
    public CategoryDto toDto(Category category) {
        if (category == null) return null;

        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .level(category.getLevel())
                .category_img(category.getCategory_img())
                .parentId(
                        category.getParentCategory() != null
                                ? category.getParentCategory().getId()
                                : null
                )
                .build();
    }

    // ========= DTO → ENTITY =========
    // NOTE: parentCategory must be set in SERVICE (not here)
    public Category toEntity(CategoryDto dto) {
        if (dto == null) return null;

        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setLevel(dto.getLevel());
        category.setCategory_img(dto.getCategory_img());
        return category;
    }
}
