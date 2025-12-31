package com.backend.ecommerce.MapStruct;

import com.backend.ecommerce.Model.PaymentInformation;
import com.backend.ecommerce.Model.Review;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Model.UserRole;
import com.backend.ecommerce.Payload.DTO.PaymentInformationDto;
import com.backend.ecommerce.Payload.DTO.UserDto;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class UserMapper {

    public static UserDto toDTO(User user){
        UserDto userDto1 = new UserDto();

        userDto1.setId(user.getId());
        userDto1.setFirstName(user.getFirstName());
        userDto1.setLastName(user.getLastName());
        userDto1.setEmail(user.getEmail());
        userDto1.setMobile(user.getMobile());
        userDto1.setRole(String.valueOf(user.getRoles()));

        userDto1.setCreateAt(user.getCreateAt());
        userDto1.setPaymentInformationDto(user.getPaymentInformations());
        userDto1.setRatings(user.getRatings());
        userDto1.setReviews(user.getReviews());
        userDto1.setStoreDto(user.getStore());
        userDto1.setAvatar(user.getAvatar());
        return userDto1;
    }
    public static User toEntity(UserDto userDto) throws IOException {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setMobile(userDto.getMobile());
        user.setRatings(userDto.getRatings());
        user.setLastName(userDto.getLastName());
        user.setCreateAt(userDto.getCreateAt());
        user.setReviews(userDto.getReviews());
        user.setEmail(userDto.getEmail());
        user.setPaymentInformations(userDto.getPaymentInformationDto());
        user.setRoles(UserRole.valueOf(userDto.getRole()));
        user.setStore(userDto.getStoreDto());

        user.setAvatar(userDto.getAvatar());
        return user;
    }

}
