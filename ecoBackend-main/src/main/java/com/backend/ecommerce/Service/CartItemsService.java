package com.backend.ecommerce.Service;

import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.Model.Cart;
import com.backend.ecommerce.Model.CartItem;
import com.backend.ecommerce.Model.Product;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Payload.DTO.CartDTO;
import com.backend.ecommerce.Payload.DTO.CartItemDTO;
import com.backend.ecommerce.Payload.DTO.ProductDto;

public interface CartItemsService {
    CartItemDTO createCartItem(Cart cart,Product product,CartItemDTO cartItemDTO) throws Exception, UserException;

    CartItemDTO updateCartItem(CartItem cartItem, Long id, Long UserId) throws Exception, UserException;

    void deleteCartItem(Long CartItemsId, Long UserId)throws Exception, UserException;
    CartItemDTO isCartItemExist(Long cartId, Long productId, String size) throws Exception, UserException;
    CartItemDTO findCartItemById(Long CartItemId, User user)throws Exception, UserException;

}
