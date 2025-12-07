package com.backend.ecommerce.Service;

import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Payload.DTO.UserDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
     User findUserById(Long Id) throws Exception, UserException;
     User findUserProfileByJwt(UserDetails Jwt) throws Exception, UserException;
     User  getCurrentUser()throws UserException;
     User getUserFromToken(UserDetails token) throws UserException, Exception;
        List<User> getAllUsers() throws Exception, UserException;
      UserDto DeleteUser(Long Id)throws Exception,UserException;


}
