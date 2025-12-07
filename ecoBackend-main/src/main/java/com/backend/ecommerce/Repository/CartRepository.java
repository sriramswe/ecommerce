package com.backend.ecommerce.Repository;

import com.backend.ecommerce.Model.Cart;
import com.backend.ecommerce.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

   @Query(
           "SELECT c From Cart c Where c.user.id =:userId"
   )
 Cart findByUser(@Param("userId") Long userId);
}
