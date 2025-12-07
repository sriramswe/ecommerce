package com.backend.ecommerce.Controller;

import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.Model.Review;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Payload.DTO.RatingDto;
import com.backend.ecommerce.Payload.DTO.ReviewDto;
import com.backend.ecommerce.Service.RatingService;
import com.backend.ecommerce.Service.ReviewService;
import com.backend.ecommerce.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rating")
public class RatingController {

    private final UserService userService;
    private final ReviewService reviewService;
    private final RatingService ratingService;

    // --------------------- REVIEW ---------------------

    @PostMapping("/reviews")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ReviewDto> createReview(
            @RequestHeader("Authorization") UserDetails jwt,
            @RequestBody ReviewDto reviewDto) {

        try {
            User user = userService.findUserProfileByJwt(jwt);
            ReviewDto created = reviewService.createReview(reviewDto, user);
            return ResponseEntity.ok(created);

        } catch (UserException | Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/review/product/{productId}")
    public ResponseEntity<List<Review>> getProductReviews(@PathVariable Long productId) throws Exception {
        List<Review> reviews = reviewService.getAllReviewByProductId(productId);
        return ResponseEntity.ok(reviews);
    }

    // --------------------- RATING ---------------------

    @PostMapping("/ratings")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<RatingDto> createProductRating(
            @RequestHeader("Authorization")UserDetails jwt,
            @RequestBody RatingDto ratingDto) {

        try {
            User user = userService.findUserProfileByJwt(jwt);
            RatingDto created = ratingService.createRating(ratingDto, user);
            return ResponseEntity.ok(created);

        } catch (UserException | Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/ratings/product/{productId}")
    public ResponseEntity<List<RatingDto>> getProductRatings(@PathVariable Long productId) throws Exception {
        List<RatingDto> ratings = ratingService.getProductRatings(productId);
        return ResponseEntity.ok(ratings);
    }
}
