package com.backend.ecommerce.Controller;


import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.Model.StoreStatus;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Payload.DTO.StoreDto;
import com.backend.ecommerce.Payload.Response.AuthResponse;
import com.backend.ecommerce.Service.StoreService;
import com.backend.ecommerce.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store")
public class StoreController {
     private final StoreService storeService;
     private final UserService userService;
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN','SELLER')")
    public ResponseEntity<StoreDto> createStore(@RequestBody StoreDto storeDto) throws Exception, UserException {
        StoreDto saved = storeService.createStore(storeDto, null);
        return ResponseEntity.ok(saved);
    }
         @GetMapping("/{id}")
         public ResponseEntity<StoreDto> getStoreByID(@PathVariable Long Id ,@RequestHeader("Authorization")UserDetails jwt) throws Exception{
                 return ResponseEntity.ok(storeService.GetStoreById(Id));

     }
    @GetMapping
    public ResponseEntity<List<StoreDto>> getAllStores() {
        return ResponseEntity.ok(storeService.getAllStores());
    }
    @GetMapping("/my-store")
    @PreAuthorize("hasAnyRole('ADMIN','SELLER')")
    public ResponseEntity<StoreDto> getMyStore() throws Exception, UserException {
        return ResponseEntity.ok(storeService.getStoreByAdmin());
    }
    @GetMapping("/employee/{userId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<StoreDto> getStoreByEmployee(@PathVariable Long userId) throws UserException {
        return ResponseEntity.ok(storeService.getStoreByEmployeeId(userId));
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SELLER')")
    public ResponseEntity<StoreDto> updateStore(@PathVariable Long id, @RequestBody StoreDto storeDto) throws Exception, UserException {
        return ResponseEntity.ok(storeService.UpdateStore(id, storeDto));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteStore(@PathVariable Long id) throws Exception, UserException {
        storeService.Delete(id);
        return ResponseEntity.ok("Store deleted successfully");
    }
    @PutMapping("/{id}/moderate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StoreDto> moderateStore(
            @PathVariable Long id,
            @RequestParam StoreStatus status) throws Exception, UserException {

        return ResponseEntity.ok(storeService.moderateStore(id, status));
    }
}
