package com.backend.ecommerce.Service;

import com.backend.ecommerce.Exception.BadRequestException;
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
}

