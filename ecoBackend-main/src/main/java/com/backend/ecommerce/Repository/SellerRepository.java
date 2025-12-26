package com.backend.ecommerce.Repository;

import com.backend.ecommerce.Model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    Optional<Seller> findByUserId(Long userId);
    Optional<Seller> findByEmail(String email); // If email is unique for sellers
    boolean existsByGstNumber(String gstNumber);
    boolean existsByEmail(String email);

}