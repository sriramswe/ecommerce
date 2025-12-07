package com.backend.ecommerce.Payload.DTO;

import com.backend.ecommerce.Model.Cart;
import com.backend.ecommerce.Model.CartItem;
import com.backend.ecommerce.Model.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.ManyToOne;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO extends CartItem {
    private Long Id;

    private Cart cart;

  private Product product;
    private Long productId;

    private String size;
    private int quantity;
    private int price;
    private int discountprice;

}
