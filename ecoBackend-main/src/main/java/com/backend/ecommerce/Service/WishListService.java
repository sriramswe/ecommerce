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

    private final ProductRepository productRepository;


    public WislistItems addToList(Product products, User user) throws  Exception{
        Product product = productRepository.findById(products.getId())
                .orElseThrow(()-> new Exception("product not found here to Add"));
              wishListRepository.findByUserAndProduct(user, product)
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
        Product product = productRepository.findById(productId)
                        .orElseThrow(()-> new RuntimeException("product not found"));
        wishListRepository.DeleteByUserAndProductId(user,product);
    }
    public List<WislistItems> getUserWishList(User user){
        return  wishListRepository.findByUser(user);

    }
}
