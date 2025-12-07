package com.backend.ecommerce.Service;

import com.backend.ecommerce.Config.JwtProvider;
import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.MapStruct.UserMapper;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Payload.DTO.UserDto;
import com.backend.ecommerce.Payload.Request.LoginRequest;
import com.backend.ecommerce.Payload.Response.AuthResponse;
import com.backend.ecommerce.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    @Autowired
    private final UserRepository userRepository;
     private final PasswordEncoder passwordEncoder;
     private final JwtProvider jwtProvider;
     private final CustomUserImpl customUser;
    @Override
    public AuthResponse createUser(UserDto userDto) throws UserException {
         User user1 = userRepository.findByEmail(String.valueOf(userDto.getId()));
         if(user1 == null){
             throw new UserException("email already exists");
         }
         if(userDto.getRole().equals("ADMIN")){
             throw new UserException("role Admin is not Allowed");
         }
          User newUSer = new User();
         newUSer.setEmail(userDto.getEmail());
         newUSer.setPassword(passwordEncoder.encode(userDto.getPassword()));
         newUSer.setRoles(userDto.getRole());
         newUSer.setFirstName(userDto.getFirstName());
         newUSer.setLastName(userDto.getLastName());
         newUSer.setCreateAt(LocalDateTime.now());

         User savedUser = userRepository.save(newUSer);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDto.getEmail(),userDto.getPassword());
        SecurityContextHolder.getContext().setAuthentication(
                authentication
        );
        String jwt = jwtProvider.generateToken((UserDetails) authentication);
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
     Authentication authentication = new UsernamePasswordAuthenticationToken(
             email,password
     );
     SecurityContextHolder.getContext().setAuthentication(
             authentication
     );
        Collection<? extends  GrantedAuthority> auths = authentication.getAuthorities();

       String roles = String.valueOf(auths);
       String jwt = jwtProvider.generateToken((UserDetails) authentication);
       User user = userRepository.findByEmail(email);
       user.setLastlogin(LocalDateTime.now());
       userRepository.save(user);
       AuthResponse response = new AuthResponse();
       response.setJwt(jwt);
       response.setMessage("Login SuccessFully");
       response.setUser(UserMapper.toDTO(user));
       return response;
    }

    @Override
    public void logoutUser(UserDetails jwt) throws UserException {
          String email  = jwtProvider.generateToken(jwt);
          User user = userRepository.findByEmail(email);
          if(user == null){
              throw new UserException("User Not Found");
          }
          user.setLastLogout(LocalDateTime.now());
          userRepository.save(user);
    }
    public Authentication authentication(String email , String password) throws UserException {
        UserDetails userDetails = customUser.loadUserByUsername(email);
        if(userDetails == null){
            throw  new UserException("User Not Found");
        }
        if (!passwordEncoder.matches(password,userDetails.getPassword())){
            throw  new UserException("Password doesn't match");
        }
         return new UsernamePasswordAuthenticationToken(userDetails, null , userDetails.getAuthorities());
    }
}
