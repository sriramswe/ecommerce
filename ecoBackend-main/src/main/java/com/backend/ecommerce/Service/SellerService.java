package com.backend.ecommerce.Service;

import com.backend.ecommerce.Exception.BadRequestException;
import com.backend.ecommerce.Payload.DTO.ProductDto;
import com.backend.ecommerce.Payload.DTO.SellerDTO;
import com.backend.ecommerce.Payload.DTO.SellerRequestDto;

import java.util.List;

public interface SellerService {
    SellerDTO registerNewSeller(SellerRequestDto sellerRequestDto) throws BadRequestException;
    SellerDTO createSellerForExistingUser(Long userId, SellerRequestDto sellerRequestDto) throws BadRequestException, Exception;
    SellerDTO getSellerById(Long id);
    SellerDTO getSellerByUserId(Long userId);
    List<SellerDTO> getAllSellers();
    SellerDTO updateSeller(Long id, SellerRequestDto sellerRequestDto) throws BadRequestException;
    void deleteSeller(Long id);
    ProductDto createProduct(
            Long sellerId,
            ProductDto productRequestDTO
    ) throws BadRequestException;

    /* =====================================================
       GET ALL PRODUCTS BY SELLER
    ===================================================== */
    List<ProductDto> getProductsBySeller(Long sellerId);

    /* =====================================================
       GET SINGLE PRODUCT (SELLER OWNED)
    ===================================================== */
    ProductDto getProductById(
            Long sellerId,
            Long productId
    ) throws org.apache.coyote.BadRequestException;

    /* =====================================================
       UPDATE PRODUCT (SELLER OWNED)
    ===================================================== */
    ProductDto updateProduct(
            Long sellerId,
            Long productId,
            ProductDto productRequestDTO
    ) throws BadRequestException;

    /* =====================================================
       DELETE PRODUCT (SELLER OWNED)
    ===================================================== */
    void deleteProduct(
            Long sellerId,
            Long productId
    ) throws BadRequestException;


}

