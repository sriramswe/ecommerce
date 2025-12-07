package com.backend.ecommerce.Controller;

import com.backend.ecommerce.Exception.OrderException;
import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.Model.Order;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Payload.DTO.AddressDto;
import com.backend.ecommerce.Payload.DTO.OrderDto;
import com.backend.ecommerce.Service.OrderService;
import com.backend.ecommerce.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/Order")
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderDto> CreateOrder(@RequestHeader("Authorization")String jwt,
                                                @RequestBody AddressDto shippingAddress
                                                ) throws Throwable {
        try{

            User user = userService.getCurrentUser();
            OrderDto orderDto = orderService.createOrder(user,shippingAddress);
            return new ResponseEntity<>(orderDto, HttpStatus.OK);
        } catch (UserException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new Throwable(String.valueOf(HttpStatus.BAD_REQUEST));
        }
    }
    @GetMapping("/user-history")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<OrderDto>> getUserOrderHistory(@RequestHeader("Authorization") String jwt){
        try{
            User user = userService.getCurrentUser();
         List<OrderDto> orderDtos = orderService.UsersOrderHistory(user.getId());
         return  new ResponseEntity<>(orderDtos,HttpStatus.OK);
        } catch (UserException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<OrderDto> findOrderById(@RequestHeader("Authorization") String jwt,
                                                  @PathVariable Long orderId
                                                 )throws Exception{
        try{
            User user = userService.getCurrentUser();
            OrderDto orderDto = orderService.findOrderById(user.getId());
            return new ResponseEntity<>(orderDto,HttpStatus.OK);
        } catch (UserException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/admin/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderDto>> getAllOrders(){
        List<OrderDto> order = orderService.getALLOrders();
        return new ResponseEntity<>(order,HttpStatus.OK);
    }
    @PutMapping("/admin/{orderId}/place")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderDto> placeOrder(@PathVariable Long orderId){
        try{
            OrderDto orderDto = orderService.PlaceOrder(orderId);
            return  new ResponseEntity<>(orderDto,HttpStatus.OK);
        }
        catch (OrderException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/admin/{orderId}/confirm")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderDto> confirmOrder(@PathVariable Long orderId){
        try{
            OrderDto orderDto = orderService.confirmedOrder(orderId);
            return  new ResponseEntity<>(orderDto,HttpStatus.OK);
        }catch (
            OrderException orderException
        ){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
  @PutMapping("/admin/{orderId}/Ship")
  @PreAuthorize("hasRole('ADMIN')")
 public ResponseEntity<OrderDto> shipOrder(@PathVariable Long OrderId){
        try{
            OrderDto orderDto = orderService.ShippingOrder(OrderId);
            return new ResponseEntity<>(orderDto, HttpStatus.OK);
        }catch (OrderException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
  }
  @PutMapping("/admin/{orderId}/deliver")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderDto>deliverOrder(@PathVariable Long OrderId){
        try{
            OrderDto orderDto = orderService.DeliveryOrder(OrderId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (OrderException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
  }
  @PutMapping("/admin/{orderId}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId){
        try{
            orderService.DeleteOrder(orderId);
            return  new ResponseEntity<>("Order Deleted Successfully",HttpStatus.NO_CONTENT);
        }catch (OrderException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
  }
  @PutMapping("/admin/{orderId}/cancel")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderDto> cancelOrder(@PathVariable Long orderId){
        try{
            OrderDto orderDto = orderService.CancelledOrder(orderId);
            return  new ResponseEntity<>(orderDto,HttpStatus.OK);
        }catch (OrderException e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
  }


}
