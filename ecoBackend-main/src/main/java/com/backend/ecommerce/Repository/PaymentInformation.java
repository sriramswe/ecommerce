package com.backend.ecommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentInformation extends JpaRepository<PaymentInformation,Long> {
}
