package com.backend.ecommerce.Payload.DTO;

import com.backend.ecommerce.Model.CartItem;
import com.backend.ecommerce.Model.User;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CartDTO {
    private Long Id;

    private User user;

    private Set<CartItem> cartItems = new HashSet<>();

    private double totalPrice;

    private int totalItems;
    private int totalDiscountPrice;

    private int discount;


}
