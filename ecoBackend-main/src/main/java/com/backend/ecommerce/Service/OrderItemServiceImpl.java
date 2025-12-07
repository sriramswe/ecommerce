package com.backend.ecommerce.Service;

import com.backend.ecommerce.Exception.OrderException; // Assuming you might need this
import com.backend.ecommerce.MapStruct.OrderItemMapper; // Assuming you have an OrderItemMapper
import com.backend.ecommerce.Model.Order;
import com.backend.ecommerce.Model.OrderItem;
import com.backend.ecommerce.Payload.DTO.OrderItemDto;
import com.backend.ecommerce.Repository.OrderItemRepository; // <-- You need this!
import com.backend.ecommerce.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Good practice for service methods that modify data

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    @Override
    public OrderItemDto createOrderItem(OrderItem orderItem) throws OrderException {
        if (orderItem.getOrder() == null || orderItem.getOrder().getId() == null) {
            throw new OrderException("Order item must be associated with an existing order.");
        }
        Optional<Order> existingOrder = orderRepository.findById(orderItem.getOrder().getId());
        if (existingOrder.isEmpty()) {
            throw new OrderException("Parent order with ID " + orderItem.getOrder().getId() + " not found.");
        }
        orderItem.setOrder(existingOrder.get());

        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
  return OrderItemMapper.toDto(savedOrderItem);
    }

    @Override
    public OrderItemDto findOrderItemById(Long orderItemId) throws OrderException {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new OrderException("Order Item not found with ID: " + orderItemId));
        return OrderItemMapper.toDto(orderItem);
    }
}