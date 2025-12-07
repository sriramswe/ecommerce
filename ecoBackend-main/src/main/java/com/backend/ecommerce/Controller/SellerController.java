package com.backend.ecommerce.Controller;

import com.backend.ecommerce.Exception.BadRequestException;
import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.Payload.DTO.SellerDTO;
import com.backend.ecommerce.Payload.DTO.SellerRequestDto;
import com.backend.ecommerce.Service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sellers")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    // Only ADMIN can create a seller for an existing user
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<SellerDTO> createSeller(@RequestParam Long userId, @RequestBody SellerRequestDto dto) throws UserException, BadRequestException, Exception {
        return ResponseEntity.ok(sellerService.createSellerForExistingUser(userId, dto));
    }

    // ADMIN or the seller himself can view a seller profile
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @GetMapping("/{id}")
    public ResponseEntity<SellerDTO> getSeller(@PathVariable Long id) {
        return ResponseEntity.ok(sellerService.getSellerById(id));
    }

    // Only ADMIN can view all sellers
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<SellerDTO>> getAll() {
        return ResponseEntity.ok(sellerService.getAllSellers());
    }

    // ADMIN or the seller himself can update
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @PutMapping("/{id}")
    public ResponseEntity<SellerDTO> updateSeller(
            @PathVariable Long id,
            @RequestBody SellerRequestDto dto) throws UserException, BadRequestException {
        return ResponseEntity.ok(sellerService.updateSeller(id, dto));
    }

    // Only ADMIN can delete
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSeller(@PathVariable Long id) {
        sellerService.deleteSeller(id);
        return ResponseEntity.ok("Seller deleted");
    }
}
