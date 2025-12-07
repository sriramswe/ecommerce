package com.backend.ecommerce.Controller;


import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Model.WislistItems;
import com.backend.ecommerce.Payload.DTO.WatchListItemsDto;
import com.backend.ecommerce.Payload.Response.ApiResponse;
import com.backend.ecommerce.Service.UserService;
import com.backend.ecommerce.Service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/WishList")
public class WatchListController {
    private final WishListService wishListService;
    private final UserService userService;


    @PostMapping("/{productId}")
    public ResponseEntity<WislistItems>addToWishList(@PathVariable Long productId,@RequestHeader("Authorization") String Jwt) throws UserException,Exception{
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(wishListService.addToList(productId,user));

    }
    @GetMapping("/{productID}")
    public ResponseEntity<ApiResponse> RemoveWishList(@PathVariable Long productID , @RequestHeader("Authorization") String Jwt)throws Exception , UserException{


        User user = userService.getCurrentUser();
        wishListService.removeWishlist(productID,user);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Successfully Deleted WatchList");
      return ResponseEntity.ok(apiResponse);

     }

     @GetMapping
    public ResponseEntity<WislistItems> getAllWish(@RequestHeader String Jwt) throws Exception,UserException{
        User user = userService.getCurrentUser();
        return ResponseEntity.ok((WislistItems) wishListService.getALlWishList(user));
     }
}
