package com.backend.ecommerce.Model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id; // changed from String to Long

    @Column(nullable = false)
    private String brand;
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;


    @OneToOne
    private User storeAdmin;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String description;

    private String storeType;
    private StoreStatus status;

    @Embedded
    private StoreContent content = new StoreContent();
    @PrePersist
    protected void onCreate(){
        createAt = LocalDateTime.now();
        status = StoreStatus.PENDING;
    }
    @PreUpdate
    protected  void onUpdate(){
        updateAt = LocalDateTime.now();
    }

}
