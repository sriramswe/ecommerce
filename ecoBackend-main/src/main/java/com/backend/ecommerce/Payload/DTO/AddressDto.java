package com.backend.ecommerce.Payload.DTO;

import com.backend.ecommerce.Model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Data
public class AddressDto {
    private Long id;
    private String firstName;
    private String LastName;
   private String streetAddress;
    private String city;
   private String State;
    private String zipcode;
    private UserDto user;
    private String mobile;
}
