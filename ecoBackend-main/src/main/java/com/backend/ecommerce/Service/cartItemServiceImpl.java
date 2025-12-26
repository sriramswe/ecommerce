package com.backend.ecommerce.Service;

import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.MapStruct.CartItemsMapper;
import com.backend.ecommerce.Model.Cart;
import com.backend.ecommerce.Model.CartItem;
import com.backend.ecommerce.Model.Product;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Payload.DTO.CartItemDTO;
import com.backend.ecommerce.Repository.CartItemRepository;
import com.backend.ecommerce.Repository.CartRepository;
import com.backend.ecommerce.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class cartItemServiceImpl implements CartItemsService{
private final CartItemRepository cartItemRepository;
private final UserServiceImpl userService;
private final CartRepository cartRepository;
private final ProductRepository productRepository;
    @Override
    public CartItemDTO createCartItem(Cart cart, Product product, CartItemDTO cartItemDTO) throws Exception, UserException {
        CartItem newcartItem = new CartItem();
        newcartItem.setProduct(product);
        newcartItem.setSize(cartItemDTO.getSize());
        newcartItem.setQuantity(cartItemDTO.getQuantity());
        newcartItem.setPrice(product.getPrice() * cartItemDTO.getQuantity());
        newcartItem.setDiscountPrice(product.getDiscountedPrice()* cartItemDTO.getQuantity());
        newcartItem.setCart(cart);
       CartItem savedItem = cartItemRepository.save(newcartItem);
     return CartItemsMapper.toDTO(savedItem);
    }

    @Override
    public CartItemDTO updateCartItem(CartItem cartItem, Long id, Long UserId) throws Exception, UserException {
       User requesting  = userService.findUserById(id);
       Cart userCart = cartRepository.findByUser(requesting.getId());
        if (userCart == null || !userCart.getCartItems().contains(cartItem)) {
            throw new UserException("You are not authorized to update this cart item.");
        }

        // The cartItem object passed in should already have its quantity/price updated by CartServiceImpl.
        // We just need to persist it.
        CartItem updated = cartItemRepository.save(cartItem);
        return CartItemsMapper.toDTO(updated);
    }

    @Override
    public void deleteCartItem(Long CartItemsId, Long UserId) throws Exception, UserException {
            CartItem cartItemDelete = cartItemRepository.findById(CartItemsId)
                    .orElseThrow(()-> new Exception("Cart item not found with id:"+CartItemsId));
             User requestingUser = userService.findUserById(UserId);
             Cart userCart = cartRepository.findByUser(requestingUser.getId());
             if(userCart == null || !userCart.getCartItems().contains(cartItemDelete)){
                 throw  new UserException("you are not authorized to delete this cart item");
             }
              userCart.getCartItems().remove(cartItemDelete);
             cartItemRepository.delete(cartItemDelete);
    }

    @Override
    public CartItemDTO isCartItemExist(Long cartId, Long productId, String size) throws Exception, UserException {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new Exception("Cart not found with id: " + cartId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new Exception("Product not found with id: " + productId));
        CartItem existingItem = cartItemRepository.isCartItemExist(cart, product, size);
        if (existingItem != null) {
            return CartItemsMapper.toDTO(existingItem);
        }
        return null;
    }


    @Override
    public CartItemDTO findCartItemById(Long cartItemId, User user) throws Exception, UserException {
            CartItem cartItem = cartItemRepository.findById(cartItemId)
                    .orElseThrow(() -> new Exception("Cart item not found by Id:" + cartItemId));

            Cart userCart = cartRepository.findByUser(user.getId());
            if (userCart == null) {
                throw new UserException("User does not have an active cart."); // Changed to UserException for consistency
            }

            boolean itemBelongsToUserCart = userCart.getCartItems().stream()
                    .anyMatch(item -> item.getId().equals(cartItem.getId()));

            if (!itemBelongsToUserCart) {
                throw new UserException("You are not authorized to view this cart item."); // Changed to UserException
            }

            return CartItemsMapper.toDTO(cartItem);
        }



}
