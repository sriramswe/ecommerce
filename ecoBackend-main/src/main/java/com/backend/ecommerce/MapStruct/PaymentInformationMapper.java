package com.backend.ecommerce.MapStruct;



import com.backend.ecommerce.Model.PaymentInformation;
import com.backend.ecommerce.Payload.DTO.PaymentInformationDto;
import org.springframework.stereotype.Component;

@Component
public class PaymentInformationMapper {
    public static PaymentInformationDto toDto(PaymentInformation info) {
        if (info == null) return null;
        return PaymentInformationDto.builder()
                .cardholderName(info.getCardholderName())
                .cardNumber(info.getCardNumber())
                .expirationDate(info.getExpirationDate())
                .cvv(info.getCvv())
                .build();
    }
    public static PaymentInformation toEntity(PaymentInformationDto dto) {
        if (dto == null) return null;
        PaymentInformation info = new PaymentInformation();
        info.setCardholderName(dto.getCardholderName());
        info.setCardNumber(dto.getCardNumber());
        info.setExpirationDate(dto.getExpirationDate());
        info.setCvv(dto.getCvv());
        return info;
    }
}
