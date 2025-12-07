package com.backend.ecommerce.Controller;

import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Payload.DTO.CartDTO;
import com.backend.ecommerce.Payload.DTO.CartItemDTO;
import com.backend.ecommerce.Service.CartService;
import com.backend.ecommerce.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/cart")
@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<CartDTO> findUSerCart(@RequestHeader("Authorization")String jwt) throws Throwable {
        try{
            User user = userService.getCurrentUser();
           CartDTO cartDTO = cartService.findUserCart(user.getId());
           return new ResponseEntity<>(cartDTO, HttpStatus.OK);
        } catch (UserException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new Throwable(String.valueOf(HttpStatus.BAD_REQUEST));
        }
    }
    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<String> addTOCart(@RequestHeader("Authorization") String Jwt,
                                            @RequestBody CartItemDTO cartItemDTO){
        try{
          User user = userService.getCurrentUser();
          String response = cartService.AddCartItems(user.getId(),cartItemDTO);
          return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (UserException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
