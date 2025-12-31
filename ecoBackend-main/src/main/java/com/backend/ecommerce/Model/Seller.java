package com.backend.ecommerce.Model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sellers")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String companyName;
    private String shortName;
    private String address;
    private String city;
    private String pinCode;

    @OneToOne
    @JoinColumn(name = "user_Id",referencedColumnName = "id")
    private User user;


    private String randomPassword;


    private String msmeNumber;
    @Column(nullable = false,unique = true)
    private String gstNumber;
    @Embedded
    private BankDetails bankDetails;
    private String photoUrl;
    private String email;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
