package com.backend.ecommerce.Controller;

import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Payload.DTO.CartItemDTO;
import com.backend.ecommerce.Service.CartItemsService;
import com.backend.ecommerce.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/cart-item")
@RestController
@RequiredArgsConstructor
public class CartItemController {
  private  final CartItemsService cartItemsService;
  private final UserService userService;

  @GetMapping("/{cartItemId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<CartItemDTO> findCartItemById(@RequestHeader("Authorization")String jwt,
                                                        @PathVariable Long cartItemId){
       try{
           User currentUser = userService.getCurrentUser();
        CartItemDTO cartItemDTO = cartItemsService.findCartItemById(cartItemId,currentUser);
        return new ResponseEntity<>(cartItemDTO, HttpStatus.OK);
       } catch (UserException e) {
           return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
       } catch (Exception e) {
           return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
       }
  }
  @PutMapping("/{cartItemId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<CartItemDTO> updateCartItem(@RequestHeader("Authorization") String jwt,
                                                      @PathVariable long cartItemId,
                                                        @RequestBody CartItemDTO cartItemDTO){
       try{
           User currentUser = userService.getCurrentUser();
           CartItemDTO cartItemDTO1 = cartItemsService.updateCartItem(cartItemDTO,cartItemId,currentUser.getId());
           return new ResponseEntity<>(cartItemDTO1,HttpStatus.OK);
       } catch (UserException e) {
           return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
       } catch (Exception e) {
           return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
       }
  }
  @DeleteMapping("{cartItemId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<String> deleteCartItem(@RequestHeader("Authorization") String jwt, @PathVariable Long cartItemId){
      try{
          User currentUser = userService.getCurrentUser();
         cartItemsService.deleteCartItem(cartItemId, currentUser.getId());
         return  new ResponseEntity<>("Cart item removed successfully",HttpStatus.NO_CONTENT);

      } catch (UserException e) {
          return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
      } catch (Exception e) {
          return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
      }
  }


}
