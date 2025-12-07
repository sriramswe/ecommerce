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
@EqualsAndHashCode
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String Id;

    @Column(nullable = false)
    private String brand;


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
