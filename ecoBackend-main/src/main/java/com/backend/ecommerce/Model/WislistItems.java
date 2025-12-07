package com.backend.ecommerce.Model;


import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Builder
@Table(
        name = "wishlist_items",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","product_id"})
)
public class WislistItems {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
           @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;


           @ManyToOne(fetch = FetchType.LAZY)
           @JoinColumn(name = "product_id",nullable = false)
           private Product product;

}
