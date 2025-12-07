package com.backend.ecommerce.Payload.DTO;

import com.backend.ecommerce.Model.Order;
import com.backend.ecommerce.Model.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderItemDto {
    private Long Id;
    private OrderDto order;
    private ProductDto product;
    private String size;
    private int quantity;
    private Integer price;
    private Integer discountedPrice;
    private Long userId;
    private LocalDateTime deliveryDate;


}
