package com.backend.ecommerce.Service;

import com.backend.ecommerce.Exception.OrderException;
import com.backend.ecommerce.Model.OrderItem;
import com.backend.ecommerce.Payload.DTO.OrderItemDto;

import java.net.InterfaceAddress;


public interface OrderItemService {

 OrderItemDto createOrderItem(OrderItem orderItem);

    OrderItemDto findOrderItemById(Long orderItemId) throws OrderException;
}
