package com.backend.ecommerce.Repository;
import com.backend.ecommerce.Model.Cart;
import com.backend.ecommerce.Model.CartItem;
import com.backend.ecommerce.Model.Product;
import com.backend.ecommerce.Payload.DTO.ProductDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart = :cart AND ci.product = :product AND ci.size = :size")
    CartItem isCartItemExist(@Param("cart") Cart cart, @Param("product") Product product, @Param("size") String size);


    CartItem findCartItemById(Long Id);

}
