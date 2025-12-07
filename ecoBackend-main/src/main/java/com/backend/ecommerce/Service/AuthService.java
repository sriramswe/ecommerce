package com.backend.ecommerce.Service;

import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Payload.DTO.UserDto;
import com.backend.ecommerce.Payload.Request.LoginRequest;
import com.backend.ecommerce.Payload.Response.AuthResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {

    AuthResponse createUser(UserDto userDto) throws UserException;

    AuthResponse loginUser(LoginRequest loginRequest) throws UserException;


    void logoutUser(UserDetails jwt) throws UserException;
}
