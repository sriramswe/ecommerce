package com.backend.ecommerce.Service;

import com.backend.ecommerce.Exception.OrderException;
import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.MapStruct.AddressMapper;
import com.backend.ecommerce.MapStruct.OrderMapper;
import com.backend.ecommerce.Model.*;
import com.backend.ecommerce.Payload.DTO.AddressDto;
import com.backend.ecommerce.Payload.DTO.OrderDto;
import com.backend.ecommerce.Repository.AddressRespoitory;
import com.backend.ecommerce.Repository.CartRepository;
import com.backend.ecommerce.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final AddressRespoitory addressRespoitory;
    private final OrderItemService orderItemService;
    private final CartItemsService cartItemsService;

    @Override
    public OrderDto createOrder(User user, AddressDto shippingAddressDto) throws Exception, UserException {

        // 1. Fetch User Cart
        Cart userCart = cartRepository.findByUser(user.getId());
        if (userCart == null || userCart.getCartItems().isEmpty()) {
            throw new OrderException("User cart is empty. Cannot create order.");
        }

        // 2. Convert Shipping Address
        Address shippingAddress = AddressMapper.toEntity(shippingAddressDto);

        if (shippingAddress.getId() == null) {
            shippingAddress = addressRespoitory.save(shippingAddress);
        } else {
            addressRespoitory.findById(shippingAddress.getId())
                    .orElseThrow(() -> new UserException("Shipping address not found"));
        }

        // 3. Build Order Items
        Set<OrderItem> orderItems = new HashSet<>();
        double totalPrice = 0;
        double discountedTotal = 0;
        int totalItems = 0;

        for (CartItem cartItem : userCart.getCartItems()) {

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setSize(cartItem.getSize());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setDiscountedPrice(cartItem.getDiscountPrice());

            orderItems.add(orderItem);

            totalPrice += cartItem.getPrice();
            discountedTotal += cartItem.getDiscountPrice();
            totalItems += cartItem.getQuantity();
        }

        // 4. Create Order
        Order order = new Order();

        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(shippingAddress);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setTotalPrice(totalPrice);
        order.setTotalDiscountedPrice((int) discountedTotal);
        order.setTotalItems(totalItems);
        order.setDiscount((int) (totalPrice - discountedTotal));

        Order savedOrder = orderRepository.save(order);

        // 5. Save Order Items
        for (OrderItem item : orderItems) {
            item.setOrder(savedOrder);
            orderItemService.createOrderItem(item);
        }

        savedOrder.setOrderItems(new ArrayList<>(orderItems));

        // 6. Clear Cart
        for (CartItem cartItem : new HashSet<>(userCart.getCartItems())) {
            cartItemsService.deleteCartItem(cartItem.getId(), user.getId());
        }

        userCart.getCartItems().clear();
        cartRepository.save(userCart);

        return OrderMapper.toDto(savedOrder);
    }

    @Override
    public OrderDto findOrderById(Long orderId) throws Exception {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Order not found with id: " + orderId));
        return OrderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> UsersOrderHistory(Long userId) {
        List<Order> orders = orderRepository.getUsersOrders(userId);
        return orders.stream().map(OrderMapper::toDto).toList();
    }

    // ============================= ORDER STATUS LOGIC =============================

    @Override
    public OrderDto PlaceOrder(Long orderId) throws OrderException {
        Order order = getOrder(orderId);

        if (!order.getOrderStatus().equals(OrderStatus.PENDING)) {
            throw new OrderException("Order can only be placed when its status is PENDING.");
        }

        order.setOrderStatus(OrderStatus.PLACED);
        order.setOrderDate(LocalDateTime.now());

        return OrderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public OrderDto confirmedOrder(Long orderId) throws OrderException {
        Order order = getOrder(orderId);

        if (!order.getOrderStatus().equals(OrderStatus.PLACED)) {
            throw new OrderException("Order can only be confirmed if it is in PLACED status.");
        }

        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setConfirmDate(LocalDateTime.now());

        return OrderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public OrderDto ShippingOrder(Long orderId) throws OrderException {
        Order order = getOrder(orderId);

        if (!order.getOrderStatus().equals(OrderStatus.CONFIRMED)) {
            throw new OrderException("Order can only be shipped if it is CONFIRMED.");
        }

        order.setOrderStatus(OrderStatus.SHIPPED);
        order.setShippingDate(LocalDateTime.now());

        return OrderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public OrderDto DeliveryOrder(Long orderId) throws OrderException {
        Order order = getOrder(orderId);

        if (!order.getOrderStatus().equals(OrderStatus.SHIPPED)) {
            throw new OrderException("Order can only be delivered if it is SHIPPED.");
        }

        order.setOrderStatus(OrderStatus.DELIVERED);
        order.setDeliveryDate(LocalDateTime.now());

        return OrderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public OrderDto CancelledOrder(Long orderId) throws OrderException {
        Order order = getOrder(orderId);

        if (order.getOrderStatus().equals(OrderStatus.DELIVERED)
                || order.getOrderStatus().equals(OrderStatus.CANCELLED)) {
            throw new OrderException("Delivered or already cancelled orders cannot be cancelled.");
        }

        order.setOrderStatus(OrderStatus.CANCELLED);
        order.setCancelledDate(LocalDateTime.now());

        return OrderMapper.toDto(orderRepository.save(order));
    }

    // ============================= ADMIN =============================

    @Override
    public List<OrderDto> getALLOrders() {
        return orderRepository.findAll().stream().map(OrderMapper::toDto).toList();
    }

    @Override
    public void DeleteOrder(Long orderId) throws OrderException {
        Order order = getOrder(orderId);
        orderRepository.delete(order);
    }

    // ============================= PRIVATE HELPER =============================

    private Order getOrder(Long id) throws OrderException {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderException("Order not found with id: " + id));
    }
}
