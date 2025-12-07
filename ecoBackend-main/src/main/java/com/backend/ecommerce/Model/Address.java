package com.backend.ecommerce.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_Name")
    private String firstName;
    @Column(name = "Last_Name")
    private String LastName;
    @Column(name = "street_address")
    private String streetAddress;
    @Column(name = "City")
    private String city;
    @Column(name = "state")
    private String State;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "zip_code")
    private String zipcode;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String mobile;


}
