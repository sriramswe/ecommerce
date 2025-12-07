package com.backend.ecommerce.Payload.DTO;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Data
@Builder
public class PaymentInformationDto {
    private String cardholderName;

    private String cardNumber;

    private LocalDate expirationDate;

   private String cvv;

}
