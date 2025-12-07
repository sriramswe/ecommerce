package com.backend.ecommerce.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Embeddable
public class StoreContent {
  private String address;
  private String phone;
  @Email(message = "Invalid Email Format")
  private String email;


}
