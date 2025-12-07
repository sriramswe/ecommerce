package com.backend.ecommerce.Service;

import com.backend.ecommerce.Model.Rating;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Payload.DTO.RatingDto;
import com.backend.ecommerce.Payload.DTO.UserDto;

import java.util.List;

public interface RatingService {
    RatingDto createRating(RatingDto ratingDto, User user)throws Exception;
    List<RatingDto> getProductRatings(Long productId)throws Exception;

}
