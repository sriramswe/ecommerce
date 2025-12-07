package com.backend.ecommerce.Service;

import com.backend.ecommerce.Exception.OrderException;
import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Payload.DTO.AddressDto;
import com.backend.ecommerce.Payload.DTO.OrderDto;

import java.util.List;


public interface OrderService {
    OrderDto createOrder(User user , AddressDto ShippingAddress) throws UserException, Exception;
  OrderDto findOrderById(Long OrderID) throws Exception, OrderException;
  List<OrderDto> UsersOrderHistory(Long UserId) throws Exception, OrderException;
  OrderDto PlaceOrder(Long OrderId) throws OrderException;
  OrderDto confirmedOrder(Long OrderId) throws OrderException;
  OrderDto ShippingOrder(Long OrderId) throws OrderException;
  OrderDto DeliveryOrder(Long OrderId)throws OrderException;
  OrderDto CancelledOrder(Long OrderId) throws OrderException;
  List<OrderDto> getALLOrders();
  void DeleteOrder(Long OrderId)throws OrderException;
}
