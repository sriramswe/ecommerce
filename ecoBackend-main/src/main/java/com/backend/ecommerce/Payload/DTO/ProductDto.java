package com.backend.ecommerce.Payload.DTO;

import com.backend.ecommerce.Model.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class ProductDto {
    private Long id;
   private String title;


    private String description;

    private int Price;

    private int discountedPrice;

   private int quantity;

   private int doublePercent;

    private String brand;

    private String color;

    private String imageUrl;
   private List<Rating> ratings = new ArrayList<>();

   private Set<Size> sizes = new HashSet<>();

    private List<Review> reviews = new ArrayList<>();

  private Category category;
   private int numRatings;
        private Seller sellerDTO;
    private LocalDateTime created_At;

    private String topLevelCategory;
    private String SecondLevelCategory;
    private String ThirdlevelCategory;
}
