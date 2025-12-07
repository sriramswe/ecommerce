package com.backend.ecommerce.Service;


import com.backend.ecommerce.Model.Review;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Payload.DTO.ReviewDto;
import com.backend.ecommerce.Payload.DTO.UserDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewService {
    ReviewDto createReview(ReviewDto reviewDto, User user)throws  Exception;
    List<Review> getAllReviewByProductId(Long productId)throws Exception;
}
