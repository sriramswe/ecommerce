package com.backend.ecommerce.Payload.Request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
