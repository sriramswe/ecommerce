package com.backend.ecommerce.Controller;

import com.backend.ecommerce.Exception.BadRequestException;
import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.Payload.DTO.ProductDto;
import com.backend.ecommerce.Payload.DTO.SellerDTO;
import com.backend.ecommerce.Payload.DTO.SellerRequestDto;
import com.backend.ecommerce.Service.ProductService;
import com.backend.ecommerce.Service.SellerService;
import lombok.Data;
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
    private final ProductService productService;

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
    @PostMapping("/{SellerId}/products")
    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    public ResponseEntity<ProductDto> createProductBySeller(@PathVariable Long SellerId, @RequestBody ProductDto productDto) throws BadRequestException {
        return ResponseEntity.ok(sellerService.createProduct(SellerId,productDto));
    }

    @GetMapping("/{sellerId}/products/all")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<List<ProductDto>> getProductBySeller(@PathVariable Long sellerId){
        return ResponseEntity.ok(sellerService.getProductsBySeller(sellerId));
    }
    @PutMapping("/{sellerId}/products{productId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ProductDto> UpdateProductBySeller(@PathVariable Long sellerId,@PathVariable Long productId,@RequestBody ProductDto productDto) throws BadRequestException {
        return ResponseEntity.ok(sellerService.updateProduct(sellerId,productId,productDto));
    }
    @DeleteMapping("/{sellerId}/products/{productId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<String> deleteProductBySeller(@PathVariable Long sellerId,@PathVariable Long productId) throws BadRequestException {
        sellerService.deleteProduct(sellerId,productId);
        return ResponseEntity.ok("Product Deleted Successfully");
    }

}
