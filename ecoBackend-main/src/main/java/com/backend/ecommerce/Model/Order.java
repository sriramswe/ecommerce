package com.backend.ecommerce.Model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Order_Id")
    private String OrderId;

    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime OrderDate;

    private LocalDateTime deliveryDate;

    @OneToOne
    private Address ShippingAddress;

    @Embedded
    private PaymentDetails paymentDetails = new PaymentDetails();

    private double TotalPrice;

    private Integer TotalDiscountedPrice;

    private Integer Discount;
   @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private int totalItems;
    private LocalDateTime ConfirmDate;
    private LocalDateTime createdAt;
    private LocalDateTime ShippingDate;

   private LocalDateTime CancelledDate;
}