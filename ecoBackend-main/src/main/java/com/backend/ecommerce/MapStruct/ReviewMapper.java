package com.backend.ecommerce.MapStruct;

import com.backend.ecommerce.Model.Category;
import com.backend.ecommerce.Model.Review;
import com.backend.ecommerce.Payload.DTO.ReviewDto;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ReviewMapper {

    public static ReviewDto toDTO(Review review){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setUser(UserMapper.toDTO(review.getUser()));
        reviewDto.setProduct(reviewDto.getProduct());
        reviewDto.setCreateAt(review.getCreateAt());
        return reviewDto;
    }
    public static Review toEntity(ReviewDto reviewDto, Category category) throws IOException {
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setProduct(ProductMapper.toEntity(reviewDto.getProduct()));
        review.setUser(UserMapper.toEntity(reviewDto.getUser()));
        return review;
    }
}
