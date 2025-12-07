package com.backend.ecommerce.Payload.Response;

import com.backend.ecommerce.Payload.DTO.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
 private String jwt;
 private String message;
 private UserDto user;
}