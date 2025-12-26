package com.backend.ecommerce.Service;

import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.MapStruct.CartMapper;
import com.backend.ecommerce.Model.Cart;
import com.backend.ecommerce.Model.CartItem;
import com.backend.ecommerce.Model.Product;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Payload.DTO.CartDTO;
import com.backend.ecommerce.Payload.DTO.CartItemDTO;
import com.backend.ecommerce.Repository.CartRepository;
import com.backend.ecommerce.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet; // Import for initializing new cart items set
import java.util.Optional; // Already imported, but good to ensure

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final CartItemsService cartItemsService; // This will need a new method signature for createCartItem
    private final UserRepository userRepository;

   @Override
    public CartDTO createCartItem(CartDTO cartDTO) throws Exception {
       Cart cart = CartMapper.toEntity(cartDTO);
        Cart savedCart = cartRepository.save(cart);
        return CartMapper.toDto(savedCart);
    }

    @Override
    public String AddCartItems(Long userId, CartItemDTO cartItemDTO) throws Exception, UserException {
        // 1️⃣ Find user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found with id: " + userId));

        Cart cart = cartRepository.findByUser(user.getId());
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user); // Associate the cart with the user
            cart.setCartItems(new HashSet<>()); // Initialize the set of cart items
            cart = cartRepository.save(cart); // Save the newly created cart
        }


        // 3️⃣ Get product
        Product product = productService.findProductById(cartItemDTO.getProductId());

        CartItemDTO existingItem = cartItemsService.isCartItemExist(cart.getId(), product.getId(), cartItemDTO.getSize());

        if (existingItem == null) {
            CartItemDTO newItem = cartItemsService.createCartItem(cart, product, cartItemDTO);
            cart.getCartItems().add(newItem); // Add the new item to the cart's collection
        } else {
           int newQuantity = existingItem.getQuantity() + cartItemDTO.getQuantity();
            existingItem.setQuantity(newQuantity);
            existingItem.setPrice(product.getPrice() * newQuantity);
            existingItem.setDiscountPrice(product.getDiscountedPrice() * newQuantity); // Corrected typo here
            cartItemsService.updateCartItem(cartItemDTO,user.getId(),userId); // Pass existing item to service
        }

        // 7️⃣ Update totals
        // Recalculate totals after any item addition/update
        recalculateCartTotals(cart); // Extracted into a helper method for cleaner code
        cartRepository.save(cart); // Save the updated cart

        return "✅ Cart updated successfully";
    }

    @Override
    public CartDTO findUserCart(Long userId) throws Exception, UserException {
        // Handle Optional correctly for userRepository.findById
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found with id: " + userId));

        Cart cart = cartRepository.findByUser(user.getId());

        // If no cart exists, return an empty cart DTO or throw an exception,
        // depending on your business logic. For now, create an empty one if null.
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setCartItems(new HashSet<>()); // Initialize empty set
            cart = cartRepository.save(cart); // Persist the new empty cart
        }

        // Recalculate totals every time the cart is fetched to ensure freshness
        recalculateCartTotals(cart);
        cartRepository.save(cart); // Save the cart with updated totals

        return CartMapper.toDto(cart);
    }


    private void recalculateCartTotals(Cart cart) {
        double totalPrice = 0;
        double totalDiscountPrice = 0;
        int totalItems = 0;

        if (cart.getCartItems() != null) {
            for (CartItem cartItem : cart.getCartItems()) {
                totalPrice += cartItem.getPrice();
                totalDiscountPrice += cartItem.getDiscountPrice();
                totalItems += cartItem.getQuantity();
            }
        }

        cart.setTotalPrice(totalPrice);
        cart.setTotalDiscountPrice((int) totalDiscountPrice); // Cast to int if your DTO needs it as int
        cart.setTotalItems(totalItems);
        cart.setDiscount((int) (totalPrice - totalDiscountPrice));
    }
}