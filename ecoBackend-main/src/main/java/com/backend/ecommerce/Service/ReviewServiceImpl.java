package com.backend.ecommerce.Service;

import com.backend.ecommerce.MapStruct.ReviewMapper;
import com.backend.ecommerce.Model.Product;
import com.backend.ecommerce.Model.Review;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Payload.DTO.ReviewDto;
import com.backend.ecommerce.Repository.ProductRepository;
import com.backend.ecommerce.Repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService{
   private final ReviewRepository reviewRepository;
   private final ProductServiceImpl productService;
   private final ProductRepository productRepository;

    @Override
    public ReviewDto createReview(ReviewDto reviewDto, User user) throws Exception {
        Product product = productService.findProductById(reviewDto.getProduct().getId());
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setCreateAt(LocalDateTime.now());
        review = reviewRepository.save(review);
           return (ReviewDto) reviewRepository.findById(review.getId()).stream()
                   .map(ReviewMapper::toDTO)
                   .toList();


    }

    @Override
    public List<Review> getAllReviewByProductId(Long productId) throws Exception {
        return reviewRepository.GetAllProductReviews(productId);
    }
}
