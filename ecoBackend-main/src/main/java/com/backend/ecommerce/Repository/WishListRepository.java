package com.backend.ecommerce.Repository;

import com.backend.ecommerce.Model.Product;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Model.WislistItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WislistItems,Long> {
      List<WislistItems> findByUser(User user);

      Optional<WislistItems> findByUserAndProduct(User user , Product productId);
      void DeleteByUserAndProductId(User user ,Product product);


}
