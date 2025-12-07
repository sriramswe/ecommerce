package com.backend.ecommerce.Service;


import com.backend.ecommerce.Model.Product;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Model.WislistItems;
import com.backend.ecommerce.Repository.ProductRepository;
import com.backend.ecommerce.Repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final WishListRepository wishListRepository;
    private final UserService userService;
    private final ProductService productService;
    private final ProductRepository productRepository;


    public WislistItems addToList(Long productId, User user) throws  Exception{
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new Exception("product not found here to Add"));
              wishListRepository.findByUserAndProduct(user, product.getId())
                      .ifPresent(
                              w->{
                                  try{
                                      throw new Exception("Here WatchList Already added Here");
                                  }catch (Exception e){
                                         throw new RuntimeException(e);
                                  }


                              }
                      );
              WislistItems wislistItems = WislistItems.builder()
                      .product(product)
                      .user(user)
                      .build();
              return wishListRepository.save(wislistItems);
    }

    public void removeWishlist(Long productId , User user){
        wishListRepository.DeleteByUserAndProductId(user,productId);
    }
    public List<WislistItems> getALlWishList(User user){
        return  wishListRepository.findByUser(user);

    }
}
