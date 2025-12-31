package com.backend.ecommerce.MapStruct;



import com.backend.ecommerce.Model.PaymentInformation;
import com.backend.ecommerce.Payload.DTO.PaymentInformationDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PaymentInformationMapper {
    public static PaymentInformationDto toDto(PaymentInformation info) {
        if (info == null) return null;
        return PaymentInformationDto.builder()
                .cardholderName(info.getCardNumber())
                .cardNumber(info.getCardNumber())
                .expirationDate(LocalDate.parse(info.getExpiryDate()))
                .cvv(info.getCvv())
                .build();
    }
    public static PaymentInformation toEntity(PaymentInformationDto dto) {
        if (dto == null) return null;
        PaymentInformation
                 info = new PaymentInformation();
        info.setCardNumber(dto.getCardholderName());
        info.setCardNumber(dto.getCardNumber());
        info.setExpiryDate(String.valueOf(dto.getExpirationDate()));
        info.setCvv(dto.getCvv());
        return info;
    }
}
