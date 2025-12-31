package com.backend.ecommerce.Service;

import com.backend.ecommerce.Config.JwtProvider;
import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.MapStruct.UserMapper;
import com.backend.ecommerce.Model.Gender;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Model.UserRole;
import com.backend.ecommerce.Payload.DTO.UserDto;
import com.backend.ecommerce.Payload.Request.LoginRequest;
import com.backend.ecommerce.Payload.Request.UserSignupRequest;
import com.backend.ecommerce.Payload.Request.ChangePasswordRequest;
import com.backend.ecommerce.Payload.Response.AuthResponse;
import com.backend.ecommerce.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserImpl customUser;

    @Override
    public AuthResponse createUser(UserDto userDto) throws UserException {
        // This method is for backward compatibility
        // Use the UserSignupRequest version for new code
        throw new UserException("Please use UserSignupRequest endpoint");
    }

    // ✅ NEW: Proper signup with UserSignupRequest
    public AuthResponse signupUser(UserSignupRequest signupRequest)
            throws UserException {

        if (userRepository.findByEmail(signupRequest.getEmail()) != null) {
            throw new UserException("Email already exists");
        }

        User newUser = new User();
        newUser.setEmail(signupRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        newUser.setFirstName(signupRequest.getFirstName());
        newUser.setLastName(signupRequest.getLastName());
        newUser.setMobile(signupRequest.getMobile());
        newUser.setRoles(UserRole.ROLE_USER);
        newUser.setCreateAt(LocalDateTime.now());

        // ✅ Gender safe
        if (signupRequest.getGender() != null) {
            newUser.setGender(
                    Gender.valueOf(signupRequest.getGender().toUpperCase())
            );
        }

        // ✅ Avatar safe
        if (signupRequest.getAvatar() != null && !signupRequest.getAvatar().isEmpty()) {
            try {
                newUser.setAvatar(signupRequest.getAvatar().getBytes());
            } catch (IOException e) {
                throw new UserException("Avatar upload failed");
            }
        }

        User savedUser = userRepository.save(newUser);

        UserDetails userDetails =
                customUser.loadUserByUsername(savedUser.getEmail());

        String jwt = jwtProvider.generateToken(userDetails);

        AuthResponse response = new AuthResponse();
        response.setJwt(jwt);
        response.setMessage("User Registered Successfully");
        response.setUser(UserMapper.toDTO(savedUser));

        return response;
    }

    @Override
    public AuthResponse loginUser(LoginRequest loginRequest) throws UserException {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // Validate inputs
        if (email == null || email.trim().isEmpty()) {
            throw new UserException("Email is required");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new UserException("Password is required");
        }

        // Find user by email
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserException("User not found with email: " + email);
        }

        // ✅ Validate password with encoded comparison
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserException("Invalid password");
        }

        // Load user details and create authentication
        UserDetails userDetails = customUser.loadUserByUsername(email);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT
        String jwt = jwtProvider.generateToken(userDetails);

        // ✅ Update last login timestamp
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        // Build response
        AuthResponse response = new AuthResponse();
        response.setJwt(jwt);
        response.setMessage("Login Successfully");
        response.setUser(UserMapper.toDTO(user));
        return response;
    }

    @Override
    public void logoutUser(UserDetails userDetails) throws UserException {
        // ✅ Get username from UserDetails (which returns email)
        String email = userDetails.getUsername();

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserException("User Not Found");
        }

        // ✅ Update last logout timestamp
        user.setLastLogout(LocalDateTime.now());
        userRepository.save(user);

        // Clear security context
        SecurityContextHolder.clearContext();
    }

    // ✅ Authenticate user without creating new session
    public Authentication authentication(String email, String password) throws UserException {
        if (email == null || email.trim().isEmpty()) {
            throw new UserException("Email is required");
        }

        UserDetails userDetails = customUser.loadUserByUsername(email);
        if (userDetails == null) {
            throw new UserException("User Not Found");
        }

        // ✅ Password validation with proper encoding check
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new UserException("Password doesn't match");
        }

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }

    // ✅ NEW: Change password functionality
    @Override
    public void changePassword(UserDetails userDetails, ChangePasswordRequest changePasswordRequest) throws UserException {

        // Validate passwords match
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            throw new UserException("New password and confirmation password do not match");
        }

        // Validate password length
        if (changePasswordRequest.getNewPassword().length() < 6) {
            throw new UserException("New password must be at least 6 characters");
        }

        // Get user
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserException("User not found");
        }

        // Validate current password
        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            throw new UserException("Current password is incorrect");
        }

        // Ensure new password is different from current
        if (passwordEncoder.matches(changePasswordRequest.getNewPassword(), user.getPassword())) {
            throw new UserException("New password must be different from current password");
        }

        // ✅ Update password with encoding
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
    }
}




