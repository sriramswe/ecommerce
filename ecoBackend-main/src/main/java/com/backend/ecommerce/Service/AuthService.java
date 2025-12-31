package com.backend.ecommerce.Service;

import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Payload.DTO.UserDto;
import com.backend.ecommerce.Payload.Request.LoginRequest;
import com.backend.ecommerce.Payload.Request.UserSignupRequest;
import com.backend.ecommerce.Payload.Request.ChangePasswordRequest;
import com.backend.ecommerce.Payload.Response.AuthResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

public interface AuthService {

    // ✅ Deprecated: Use signupUser instead
    AuthResponse createUser(UserDto userDto) throws UserException;

    // ✅ New signup method with UserSignupRequest
    AuthResponse signupUser(UserSignupRequest signupRequest) throws UserException, IOException;

    AuthResponse loginUser(LoginRequest loginRequest) throws UserException;

    // ✅ Fixed: Parameter is UserDetails, not jwt
    void logoutUser(UserDetails userDetails) throws UserException;

    // ✅ NEW: Change password functionality
    void changePassword(UserDetails userDetails, ChangePasswordRequest changePasswordRequest) throws UserException;
}
