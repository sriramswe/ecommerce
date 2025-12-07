package com.backend.ecommerce.Repository;

import com.backend.ecommerce.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
        SELECT p FROM Product p
        WHERE 
            (:category IS NULL OR :category = '' OR LOWER(p.category.name) = LOWER(:category))
        AND
            (:minPrice IS NULL OR p.discountedPrice >= :minPrice)
        AND
            (:maxPrice IS NULL OR p.discountedPrice <= :maxPrice)
        ORDER BY 
            CASE WHEN :sort = 'price_asc' THEN p.discountedPrice END ASC,
            CASE WHEN :sort = 'price_desc' THEN p.discountedPrice END DESC,
            CASE WHEN :sort = 'newest' THEN p.created_At END DESC
    """)
    List<Product> filterProduct(
            @Param("category") String category,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("sort") String sort,
            Integer minDiscount);

    List<Product> findByCategory_Name(String category);
}


