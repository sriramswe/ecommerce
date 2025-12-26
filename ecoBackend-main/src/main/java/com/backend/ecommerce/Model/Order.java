package com.backend.ecommerce.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private String orderId;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private LocalDateTime shippingDate;
    private LocalDateTime confirmDate;
    private LocalDateTime cancelledDate;
    private LocalDateTime createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    private Address shippingAddress;

    @Embedded
    private PaymentDetails paymentDetails;

    private double totalPrice;
    private Integer totalDiscountedPrice;
    private Integer discount;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private int totalItems;
}
