package com.backend.ecommerce.MapStruct;

import com.backend.ecommerce.Model.CartItem;
import com.backend.ecommerce.Model.Cart;
import com.backend.ecommerce.Model.Product;
import com.backend.ecommerce.Payload.DTO.CartItemDTO;
import org.springframework.stereotype.Component;

@Component
public class CartItemsMapper {
    public static CartItemDTO toDTO(CartItem cartItem){
        return CartItemDTO.builder()
                .Id(cartItem.getId())
                .productId(cartItem.getProduct().getId())
                .quantity(cartItem.getQuantity())
                .price(cartItem.getProduct().getPrice()* cartItem.getQuantity())
                .product(cartItem.getProduct())
                .size(cartItem.getSize())
                .cart(cartItem.getCart())
                .discountprice(cartItem.getDiscountPrice())
                .build();
    }
    public static CartItem toEntity(Cart cart, Product product, CartItemDTO cartItemDTO){
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setSize(cartItemDTO.getSize());
        cartItem.setQuantity(cartItemDTO.getQuantity());
        // compute price based on product price and quantity
        cartItem.setPrice(product.getPrice() * cartItemDTO.getQuantity());
        cartItem.setDiscountPrice(cartItemDTO.getDiscountprice());
        return cartItem;
    }
}
