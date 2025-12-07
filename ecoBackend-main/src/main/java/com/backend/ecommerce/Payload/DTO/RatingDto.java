package com.backend.ecommerce.Payload.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Builder
@Data
public class RatingDto {
    private Long id;
    private UserDto user;
   private ProductDto product;
   private double rating;
    private LocalDateTime createAt;
}
