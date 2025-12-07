package com.backend.ecommerce.Repository;

import com.backend.ecommerce.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    @Query(
            "SELECT rev FROM Review rev Where rev.product.id = :productID"
    )
    List<Review> GetAllProductReviews(@Param("productID")Long productId);
}
