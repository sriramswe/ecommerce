package com.backend.ecommerce.Repository;

import com.backend.ecommerce.Model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Long> {
    @Query(
            "SELECT r FROM Rating r Where r.product.id = :productId"
    )
      public List<Rating> getAllByProductRating(@Param("productId")Long productId);
}
