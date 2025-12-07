package com.backend.ecommerce.MapStruct;

import com.backend.ecommerce.Model.Rating;
import com.backend.ecommerce.Payload.DTO.RatingDto;
import org.springframework.stereotype.Component;


import com.backend.ecommerce.Model.Rating;
import com.backend.ecommerce.Payload.DTO.RatingDto;
import org.springframework.stereotype.Component;

@Component
public class RatingsMapper {

    public static RatingDto toDto(Rating rating) {
        if (rating == null) return null;

        return RatingDto.builder()
                .id(rating.getId())
                .user(UserMapper.toDTO(rating.getUser()))
                .product(ProductMapper.toDto(rating.getProduct()))  // ✅ use ProductMapper
                .rating(rating.getRating())                         // ✅ include rating field
                .createAt(rating.getCreateAt())
                .build();
    }

    public static Rating toEntity(RatingDto ratingDto) {
        if (ratingDto == null) return null;

        Rating rating = new Rating();
        rating.setId(ratingDto.getId());
        rating.setRating(ratingDto.getRating());
        rating.setUser(UserMapper.toEntity(ratingDto.getUser()));
        rating.setProduct(ProductMapper.toEntity(ratingDto.getProduct()));  // ✅ convert to entity
        rating.setCreateAt(ratingDto.getCreateAt());
        return rating;
    }
}
