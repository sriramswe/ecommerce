package com.backend.ecommerce.MapStruct;

import com.backend.ecommerce.Model.PaymentDetails;
import com.backend.ecommerce.Payload.DTO.PaymentDetailsDto;
import org.springframework.stereotype.Component;

@Component
public class PaymentDetailsMapper {

    public static PaymentDetailsDto toDto(PaymentDetails paymentDetails) {
        if (paymentDetails == null) return null;

        return PaymentDetailsDto.builder()
                .paymentMethod(paymentDetails.getPaymentMethod())
                .paymentStatus(paymentDetails.getPaymentStatus())
                .Status(paymentDetails.getStatus())
                .razorpayPaymentLinkId(paymentDetails.getRazorpayPaymentLinkId())
                .razorpayPaymentLinkReferenceId(paymentDetails.getRazorpayPaymentLinkReferenceId())
                .razorpayPaymetnId(paymentDetails.getRazorpayPaymetnId())
                .razorpaymentPaymentLinkStatus(paymentDetails.getRazorpaymentPaymentLinkStatus())
                .build();
    }

    public static PaymentDetails toEntity(PaymentDetailsDto dto) {
        if (dto == null) return null;

        PaymentDetails details = new PaymentDetails();
        details.setPaymentMethod(dto.getPaymentMethod());
        details.setPaymentStatus(dto.getPaymentStatus());
        details.setStatus(dto.getStatus());
        details.setRazorpayPaymentLinkId(dto.getRazorpayPaymentLinkId());
        details.setRazorpayPaymentLinkReferenceId(dto.getRazorpayPaymentLinkReferenceId());
        details.setRazorpayPaymetnId(dto.getRazorpayPaymetnId());
        details.setRazorpaymentPaymentLinkStatus(dto.getRazorpaymentPaymentLinkStatus());
        return details;
    }
}
