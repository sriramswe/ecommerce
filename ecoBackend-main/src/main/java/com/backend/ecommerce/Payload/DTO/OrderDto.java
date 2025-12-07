package com.backend.ecommerce.Payload.DTO;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    private String orderId;
    private UserDto user; // from your existing UserDto
    private List<OrderItemDto> orderItems;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private AddressDto shippingAddress;
    private PaymentDetailsDto paymentDetails;
    private double totalPrice;
    private Integer totalDiscountedPrice;
    private Integer discount;
    private String orderStatus;
    private int totalItems;
    private LocalDateTime createdAt;
}
