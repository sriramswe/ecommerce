package com.backend.ecommerce.Service;

import com.backend.ecommerce.MapStruct.RatingsMapper;
import com.backend.ecommerce.Model.Product;
import com.backend.ecommerce.Model.Rating;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Payload.DTO.RatingDto;
import com.backend.ecommerce.Repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService{
    private final RatingRepository ratingRepository;
    private final ProductService productService;
    private final UserService userService;

    @Override
    public RatingDto createRating(RatingDto ratingDto, User user) throws Exception {
        Product product = productService.findProductById(ratingDto.getProduct().getId());
        Rating rating = new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(ratingDto.getRating());
        rating.setCreateAt(LocalDateTime.now());
        rating = ratingRepository.save(rating);
        Rating finalRating = rating;
        return ratingRepository.findById(rating.getId())
                .map(RatingsMapper::toDto)
                .orElseThrow(
                        ()-> new Exception("Rating not found with Id:"+ finalRating.getId())
                );
    }

    @Override
    public List<RatingDto> getProductRatings(Long productId) throws Exception {


        return ratingRepository.getAllByProductRating(productId).stream()
                .map(RatingsMapper::toDto)
                .toList();
    }
}
