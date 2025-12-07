package com.backend.ecommerce.Model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;


    private String description;

    private int Price;

    @Column(name = "Discounted_Price")
    private int discountedPrice;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "Double_percent")
    private int doublePercent;

    private String brand;

    private String color;

    private String imageUrl;
     @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();

     @Embedded
     @ElementCollection
     @Column(name = "sizes")
     private Set<Size> sizes = new HashSet<>();

     @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
     private List<Review> reviews = new ArrayList<>();

     @ManyToOne
     @JoinColumn(name = "category_id")
     private Category category;
     @Column(name = "num_ratings")
     private int numRatings;

  private LocalDateTime created_At;
}
