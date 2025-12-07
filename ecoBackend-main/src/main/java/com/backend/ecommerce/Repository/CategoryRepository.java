package com.backend.ecommerce.Repository;

import com.backend.ecommerce.Model.Category;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    public Category findByName(String name);

    Category findByNameAndParent(String secondLevelCategory, @NotNull @Size(max = 50) String name);
}
