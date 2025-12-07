package com.backend.ecommerce.Payload.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentDetailsDto {
    private String paymentMethod;

    private String paymentStatus;

    private String Status;
    private String razorpayPaymentLinkId;
    private String razorpayPaymentLinkReferenceId;
    private String razorpayPaymetnId;
    private String razorpaymentPaymentLinkStatus;

}
