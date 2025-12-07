package com.backend.ecommerce.Model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@AllArgsConstructor

@NoArgsConstructor
public class PaymentDetails {

    private String paymentMethod;

    private String paymentStatus;

    private String Status;
    private String razorpayPaymentLinkId;
    private String razorpayPaymentLinkReferenceId;
    private String razorpayPaymetnId;
    private String razorpaymentPaymentLinkStatus;

}
