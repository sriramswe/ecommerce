package com.backend.ecommerce.Service;

import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.Payload.DTO.CartDTO;
import com.backend.ecommerce.Payload.DTO.CartItemDTO;

public interface CartService {
   CartDTO createCartItem(CartDTO cartDTO)throws Exception;
   String AddCartItems(Long userId, CartItemDTO cartItemDTO) throws Exception, UserException;
   CartDTO findUserCart(Long userId) throws Exception, UserException;

}
