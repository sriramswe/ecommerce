package com.backend.ecommerce.Service;

import com.backend.ecommerce.Config.JwtProvider;
import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Payload.DTO.UserDto;
import com.backend.ecommerce.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public User findUserById(Long id) throws Exception, UserException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UserException("User not found with id: " + id);
    }

    @Override
    public User findUserProfileByJwt(UserDetails jwt) throws Exception, UserException {
        String email = jwtProvider.generateToken(jwt);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserException("User not found with email: " + email);
        }
        return user;
    }

    @Override
    public User getCurrentUser() throws UserException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserException("User not found");
        }
        return user;
    }

    @Override
    public User getUserFromToken(UserDetails token) throws UserException, Exception {
        String email = jwtProvider.generateToken(token);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("Invalid Token");
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() throws Exception, UserException {
        return userRepository.findAll();
    }

    @Override
    public UserDto DeleteUser(Long Id) throws Exception, UserException {
        userRepository.deleteById(Id);
        return null;
    }
}
