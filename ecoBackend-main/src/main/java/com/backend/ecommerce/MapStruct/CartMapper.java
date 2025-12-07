package com.backend.ecommerce.MapStruct;

import com.backend.ecommerce.Model.Cart;
import com.backend.ecommerce.Payload.DTO.CartDTO;
import org.springframework.stereotype.Component;


@Component
public class CartMapper {
    public static CartDTO toDto(Cart cart){
        return CartDTO.builder()
                .Id(cart.getId())
                .user(cart.getUser()!=null?cart.getUser():null)
                .cartItems(cart.getCartItems())
                .discount(cart.getDiscount())
                .totalDiscountPrice(cart.getTotalDiscountPrice())
                .totalPrice(cart.getTotalPrice())
                .totalItems(cart.getTotalItems())
                .cartItems(cart.getCartItems())
                .build();

    }
    public static Cart toEntity(CartDTO cartDTO){
        Cart cart = new Cart();
        cart.setCartItems(cartDTO.getCartItems());
        cart.setId(cartDTO.getId());
        cart.setDiscount(cartDTO.getDiscount());
        cart.setTotalPrice(cartDTO.getTotalPrice());
        cart.setTotalItems(cartDTO.getTotalItems());
        cart.setTotalDiscountPrice(cartDTO.getTotalDiscountPrice());
        cart.setUser(cartDTO.getUser());
        return cart;
    }
}
