package com.backend.ecommerce.Controller;

import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.Payload.DTO.UserDto;
import com.backend.ecommerce.Payload.Request.LoginRequest;
import com.backend.ecommerce.Payload.Response.AuthResponse;
import com.backend.ecommerce.Service.AuthService;
import com.backend.ecommerce.Service.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody UserDto user) throws UserException {
        AuthResponse auth = authService.createUser(user);
        ResponseCookie jwtCookie = ResponseCookie.from("token", auth.getJwt())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(24 * 60 * 60)
                .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(auth);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> Login(@RequestBody LoginRequest loginRequest) throws UserException {
        AuthResponse auth = authService.loginUser(loginRequest);
        ResponseCookie jwtCookie = ResponseCookie.from("token", auth.getJwt())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(24 * 60 * 60)
                .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(auth);
    }
}


