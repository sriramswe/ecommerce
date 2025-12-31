package com.backend.ecommerce.Repository;

import com.backend.ecommerce.Model.Category;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    public Category findByName(String name);

    Category findByNameAndParentCategory(String name, Category parentCategory);
    boolean existsByParentCategory_Id(Long parentId);

    @Deprecated
    default Category findByNameAndParent(String name, String parent) {
        // This method is deprecated - use findByNameAndParentCategory instead
        return null;
    }
    List<Category> findByParentCategory_Id(Long parentId);
    List<Category> findByParentCategoryIsNull();

}
