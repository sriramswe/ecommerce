package com.backend.ecommerce.Service;

import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.Model.User;

import org.springframework.stereotype.Service;


public interface CustomUserService {

    User findUserById(Long Id) throws UserException;
}
