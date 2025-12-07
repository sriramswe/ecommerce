package com.backend.ecommerce.Payload.DTO;

import com.backend.ecommerce.Model.Product;
import com.backend.ecommerce.Model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long Id;
    private ProductDto product;
    private UserDto user;
    private LocalDateTime createAt;

}
