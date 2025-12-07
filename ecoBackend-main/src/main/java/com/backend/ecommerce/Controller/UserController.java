package com.backend.ecommerce.Controller;

import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.MapStruct.UserMapper;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Payload.DTO.UserDto;
import com.backend.ecommerce.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<UserDto> getUserProfile(@RequestHeader("Authorization")String jwt){
                try {
                    User userDto = userService.getCurrentUser();
                    return new ResponseEntity<>(UserMapper.toDTO(userDto), HttpStatus.OK);
                } catch (UserException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

    }
    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
      try{
          List<User> users  = userService.getAllUsers();
         return ResponseEntity.ok(Collections.singletonList(UserMapper.toDTO((User) users)));
      } catch (UserException e) {
          throw new RuntimeException(e);
      } catch (Exception e) {
          throw new RuntimeException(e);
      }
    }
    @GetMapping("/{ID}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long ID){
        try{
            User user = userService.findUserById(ID);
            return ResponseEntity.ok(UserMapper.toDTO(user));
        } catch (UserException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @DeleteMapping("/{Id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> deleteUser(@PathVariable Long Id){
        try{
       UserDto userDto = userService.DeleteUser(Id);
            return ResponseEntity.ok(userDto);
        } catch (UserException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
