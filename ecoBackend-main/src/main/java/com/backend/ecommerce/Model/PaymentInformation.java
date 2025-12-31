package com.backend.ecommerce.Model;

import com.backend.ecommerce.Model.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

@Table(name = "payment_information")
public class PaymentInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardNumber;
    private String expiryDate;
    private String cvv;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
