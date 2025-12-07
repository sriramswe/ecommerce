package com.backend.ecommerce.MapStruct;

import com.backend.ecommerce.Model.Order;
import com.backend.ecommerce.Model.OrderStatus;
import com.backend.ecommerce.Payload.DTO.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public static OrderDto toDto(Order order) {
        if (order == null) return null;

        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setOrderId(order.getOrderId());
        dto.setOrderDate(order.getOrderDate());
        dto.setDeliveryDate(order.getDeliveryDate());
        dto.setDiscount(order.getDiscount());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setOrderStatus(String.valueOf(order.getOrderStatus()));
        dto.setTotalItems(order.getTotalItems());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setTotalDiscountedPrice(order.getTotalDiscountedPrice());

        // User mapping
        if (order.getUser() != null) {
            dto.setUser(UserMapper.toDTO(order.getUser()));
        }

        // Address mapping
        if (order.getShippingAddress() != null) {
            dto.setShippingAddress(AddressMapper.toDto(order.getShippingAddress()));
        }

        // PaymentDetails mapping
        if (order.getPaymentDetails() != null) {


            dto.setPaymentDetails(PaymentDetailsMapper.toDto(order.getPaymentDetails()));
        }

        // OrderItems mapping
        if (order.getOrderItems() != null) {
            dto.setOrderItems(
                    order.getOrderItems().stream()
                            .map(OrderItemMapper::toDto)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public static Order toEntity(OrderDto dto) {
        if (dto == null) return null;

        Order order = new Order();
        order.setId(dto.getId());
        order.setOrderId(dto.getOrderId());
        order.setOrderDate(dto.getOrderDate());
        order.setDeliveryDate(dto.getDeliveryDate());
        order.setDiscount(dto.getDiscount());
        order.setCreatedAt(dto.getCreatedAt());
        order.setOrderStatus(OrderStatus.valueOf(dto.getOrderStatus()));
        order.setTotalItems(dto.getTotalItems());
        order.setTotalPrice(dto.getTotalPrice());
        order.setTotalDiscountedPrice(dto.getTotalDiscountedPrice());

        // User mapping
        if (dto.getUser() != null) {
            order.setUser(UserMapper.toEntity(dto.getUser()));
        }

        // Address mapping
        if (dto.getShippingAddress() != null) {
            order.setShippingAddress(AddressMapper.toEntity(dto.getShippingAddress()));
        }

        // PaymentDetails mapping
        if (dto.getPaymentDetails() != null) {
            order.setPaymentDetails(PaymentDetailsMapper.toEntity(dto.getPaymentDetails()));
        }

        // OrderItems mapping
        if (dto.getOrderItems() != null) {
            order.setOrderItems(
                    dto.getOrderItems().stream()
                            .map(OrderItemMapper::toEntity)
                            .collect(Collectors.toList())
            );
        }

        return order;
    }
}
