package com.backend.ecommerce.MapStruct;

import com.backend.ecommerce.Model.OrderItem;
import com.backend.ecommerce.Payload.DTO.OrderItemDto;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {
    public static OrderItemDto toDto(OrderItem item) {
        if (item == null) return null;
        OrderItemDto dto = new OrderItemDto();
        dto.setId(item.getId());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        dto.setDiscountedPrice(item.getDiscountedPrice());
        dto.setProduct(ProductMapper.toDto(item.getProduct()));

        return dto;
    }

    public static OrderItem toEntity(OrderItemDto dto) {
        if (dto == null) return null;
        OrderItem item = new OrderItem();
        item.setId(dto.getId());
        item.setQuantity(dto.getQuantity());
        item.setPrice(dto.getPrice());
        item.setDiscountedPrice(dto.getDiscountedPrice());
        item.setProduct(ProductMapper.toEntity(dto.getProduct()));
        return item;
    }
}
