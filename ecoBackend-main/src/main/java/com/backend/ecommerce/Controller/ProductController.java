package com.backend.ecommerce.Controller;


import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.Model.Product;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Payload.DTO.ProductDto;
import com.backend.ecommerce.Payload.Response.ApiResponse;
import com.backend.ecommerce.Service.ProductService;
import com.backend.ecommerce.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ProductController {
              private final ProductService productService;
             private final UserService userService;
    @PostMapping
    @PreAuthorize("hasAnyRole('SELLER','ADMIN')")
    public ResponseEntity<ProductDto> createProduct(
            @RequestHeader("Authorization") String jwt,
            @RequestBody ProductDto productDto) throws Exception, UserException {

        User user = userService.getCurrentUser();
        return ResponseEntity.ok(productService.createProduct(productDto, user));
    }


    @GetMapping("/product")
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) List<String> color,
            @RequestParam(required = false) List<String> sizes,
            @RequestParam(required = false, defaultValue = "0") Integer minPrice,
            @RequestParam(required = false, defaultValue = "100000") Integer maxPrice,
            @RequestParam(required = false, defaultValue = "0") Integer minDiscount,
            @RequestParam(required = false, defaultValue = "price_asc") String sort,
            @RequestParam(required = false) String stock,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        Page<Product> products = productService.getAllProduct(
                category, color, sizes, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize
        );
        return  ResponseEntity.ok(products);
    }

          @GetMapping("/product/Id/{productId}")
          public ResponseEntity<Product> FindProductByIdHandler(@PathVariable Long productId)throws Exception{
                      Product product = productService.findProductById(productId);
                      return  ResponseEntity.ok(
                              product
                      );
          }
          @PutMapping("/{productId}")
          @PreAuthorize("hasRole('ADMIN')")
      public ResponseEntity<ProductDto> updateProductHandler(@PathVariable Long productId,
                                                             @Valid @RequestBody ProductDto productDto,
                                                              @RequestHeader("Authorization") UserDetails Jwt
                                                             ){
              try{

                  User currentUSer = userService.findUserProfileByJwt(Jwt);
                  ProductDto updatedProduct = productService.updateProduct(productDto,productId,null);
                  return  ResponseEntity.ok(updatedProduct);
              } catch (UserException e) {
                    throw new RuntimeException(e);

              }          catch (Exception e) {
                  return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
              }
          }
          @DeleteMapping("/{productID}")
          @PreAuthorize("hasRole('ADMIN')")
     public ResponseEntity<ApiResponse> deleteProductHandler(@PathVariable Long productId,@RequestHeader("Authorization") UserDetails Jwt){
              try{
                  User currentUser = userService.findUserProfileByJwt(Jwt);
              productService.DeleteProduct(productId,currentUser);
              ApiResponse apiResponse = new ApiResponse();
              apiResponse.setMessage("Product deleted successfully");
              apiResponse.setSuccess(true);
              return  ResponseEntity.ok(apiResponse);

              } catch (UserException e) {
                  throw new RuntimeException(e);
              } catch (Exception e) {
                  throw new RuntimeException(e);
              }
          }



}
