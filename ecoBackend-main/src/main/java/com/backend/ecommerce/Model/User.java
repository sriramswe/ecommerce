package com.backend.ecommerce.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String FirstName;

    private String LastName;

    private String Password;

    private String email;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private UserRole roles;

    private String mobile;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Address> address = new ArrayList<>();

    @Embedded
    @ElementCollection
    @CollectionTable(name = "payment_information")
    private List<PaymentInformation> paymentInformations = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Rating> ratings = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();
    @ManyToOne
    private Store store;
    private LocalDateTime createAt;
    private LocalDateTime Lastlogin;
    private LocalDateTime LastLogout;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
