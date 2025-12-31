package com.backend.ecommerce.Controller;

import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Payload.Request.ChangePasswordRequest;
import com.backend.ecommerce.Payload.Request.LoginRequest;
import com.backend.ecommerce.Payload.Request.UserSignupRequest;
import com.backend.ecommerce.Payload.Response.AuthResponse;
import com.backend.ecommerce.Repository.UserRepository;
import com.backend.ecommerce.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;
    private final PasswordEncoder passwordEncoder ;
    private final UserRepository userRepository;
    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AuthResponse> signup(
            @ModelAttribute UserSignupRequest request
    ) throws Exception, UserException {

        AuthResponse response = authService.signupUser(request);
        return ResponseEntity.ok(response);
    }


    // ✅ Login endpoint
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) throws UserException {
        AuthResponse auth = authService.loginUser(loginRequest);

        // ✅ Set JWT in cookie (httpOnly for security)
        ResponseCookie jwtCookie = ResponseCookie.from("token", auth.getJwt())
                .httpOnly(true)
                .secure(false) // ✅ Set to true in HTTPS production
                .path("/")
                .maxAge(24 * 60 * 60)
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header("Authorization", "Bearer " + auth.getJwt()) // ✅ Also return in header for SPA
                .body(auth);
    }

    // ✅ NEW: Logout endpoint
    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout() throws UserException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            throw new UserException("User not authenticated");
        }

        authService.logoutUser((UserDetails) authentication.getPrincipal());

        ResponseCookie jwtCookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .build();

        AuthResponse response = new AuthResponse(null, "Logged out successfully", null);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(response);
    }


    // ✅ NEW: Change password endpoint
    @PostMapping("/change-password")
    public ResponseEntity<AuthResponse> changePassword(
            @Valid @RequestBody ChangePasswordRequest changePasswordRequest) throws UserException {

        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserException("User not authenticated");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        authService.changePassword(userDetails, changePasswordRequest);

        AuthResponse response = new AuthResponse();
        response.setMessage("Password changed successfully");
        response.setJwt(null);
        response.setUser(null);

        return ResponseEntity.ok(response);
    }
}


