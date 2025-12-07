package com.backend.ecommerce.MapStruct;

import com.backend.ecommerce.Model.BankDetails;
import com.backend.ecommerce.Model.Seller;
import com.backend.ecommerce.Payload.DTO.BankDetailsDTO;
import com.backend.ecommerce.Payload.DTO.SellerDTO;

import org.springframework.stereotype.Component;

@Component
public class SellerMappers {

    // ENTITY → DTO
    public SellerDTO toDto(Seller seller) {

        if (seller == null) return null;

        SellerDTO dto = new SellerDTO();

        dto.setId(seller.getId());
        dto.setCompanyName(seller.getCompanyName());
        dto.setShortName(seller.getShortName());
        dto.setAddress(seller.getAddress());
        dto.setCity(seller.getCity());
        dto.setPinCode(seller.getPinCode());
        dto.setGstNumber(seller.getGstNumber());
        dto.setMsmeNumber(seller.getMsmeNumber());
        dto.setPhotoUrl(seller.getPhotoUrl());


        if (seller.getUser() != null) {
            dto.setUserId(seller.getUser().getId());
        }

            dto.setBankDetails(toBankDto(seller.getBankDetails()));
            dto.setBankDetailsDTO(toBankDto(seller.getBankDetails()));
        }

        return dto;
    }


    // DTO → ENTITY (Create)
        if (dto == null) return null;
        Seller seller = new Seller();

        seller.setCompanyName(dto.getCompanyName());
        seller.setShortName(dto.getShortName());
        seller.setAddress(dto.getAddress());
        seller.setCity(dto.getCity());
        seller.setPinCode(dto.getPinCode());
        seller.setEmail(dto.getEmail());
        seller.setGstNumber(dto.getGstNumber());
        seller.setMsmeNumber(dto.getMsmeNumber());
        seller.setPhotoUrl(dto.getPhotoUrl());

        if (dto.getBankDetails() != null) {
            seller.setBankDetails(toBankEntity(dto.getBankDetails()));
        }

        return seller;
    }


    // DTO → EXISTING ENTITY (Update from SellerDTO)
    public void updateEntity(SellerDTO dto, Seller seller) {

        if (dto.getCompanyName() != null) seller.setCompanyName(dto.getCompanyName());
        if (dto.getShortName() != null) seller.setShortName(dto.getShortName());
        if (dto.getAddress() != null) seller.setAddress(dto.getAddress());
        if (dto.getCity() != null) seller.setCity(dto.getCity());
        if (dto.getPinCode() != null) seller.setPinCode(dto.getPinCode());
        if (dto.getEmail() != null) seller.setEmail(dto.getEmail());
        if (dto.getGstNumber() != null) seller.setGstNumber(dto.getGstNumber());
        if (dto.getMsmeNumber() != null) seller.setMsmeNumber(dto.getMsmeNumber());
        if (dto.getPhotoUrl() != null) seller.setPhotoUrl(dto.getPhotoUrl());

        if (dto.getBankDetails() != null) {
            BankDetails bank = seller.getBankDetails();

            if (bank == null) bank = new BankDetails();

            BankDetailsDTO bd = dto.getBankDetails();

            if (bd.getAccountNumber()!= null) bank.setAccoutnNumber(bd.getAccountNumber());
            if (bd.getUPIId() != null) bank.setUpiId(bd.getUPIId());
            if (bd.getAccountName() != null) bank.setAccountName(bd.getAccountName());
            if (bd.getBankName() != null) bank.setBankName(bd.getBankName());

            seller.setBankDetails(bank);
        }
    }


    // Helper: BankDetails → DTO
    private BankDetailsDTO toBankDto(BankDetails bank) {
        BankDetailsDTO dto = new BankDetailsDTO();
        dto.setAccountNumber(bank.getAccoutnNumber());
        dto.setUPIId(bank.getUpiId());
        dto.setAccountName(bank.getAccountName());
        dto.setBankName(bank.getBankName());
        return dto;
    }

    // Helper: DTO → BankDetails
    private BankDetails toBankEntity(BankDetailsDTO dto) {
        BankDetails bank = new BankDetails();
        bank.setAccoutnNumber(dto.getAccountNumber());
        bank.setUpiId(dto.getUPIId());
        bank.setAccountName(dto.getAccountName());
        bank.setBankName(dto.getBankName());
        return bank;
    }

    // Update existing Seller entity from a SellerRequestDto (used for updates)
    }
        if (sellerRequestDto == null || existingSeller == null) return;

        if (sellerRequestDto.getCompanyName() != null) existingSeller.setCompanyName(sellerRequestDto.getCompanyName());
        if (sellerRequestDto.getShortName() != null) existingSeller.setShortName(sellerRequestDto.getShortName());
        if (sellerRequestDto.getAddress() != null) existingSeller.setAddress(sellerRequestDto.getAddress());
        if (sellerRequestDto.getCity() != null) existingSeller.setCity(sellerRequestDto.getCity());
        if (sellerRequestDto.getPinCode() != null) existingSeller.setPinCode(sellerRequestDto.getPinCode());
        if (sellerRequestDto.getEmail() != null) existingSeller.setEmail(sellerRequestDto.getEmail());
        if (sellerRequestDto.getGstNumber() != null) existingSeller.setGstNumber(sellerRequestDto.getGstNumber());
        if (sellerRequestDto.getMsmeNumber() != null) existingSeller.setMsmeNumber(sellerRequestDto.getMsmeNumber());
        if (sellerRequestDto.getPhotoUrl() != null) existingSeller.setPhotoUrl(sellerRequestDto.getPhotoUrl());

        if (sellerRequestDto.getBankDetails() != null) {
            BankDetails bank = existingSeller.getBankDetails();
            if (bank == null) bank = new BankDetails();

            BankDetailsDTO bd = sellerRequestDto.getBankDetails();
            if (bd.getAccountNumber() != null) bank.setAccoutnNumber(bd.getAccountNumber());
            if (bd.getUPIId() != null) bank.setUpiId(bd.getUPIId());
            if (bd.getAccountName() != null) bank.setAccountName(bd.getAccountName());
            if (bd.getBankName() != null) bank.setBankName(bd.getBankName());

            existingSeller.setBankDetails(bank);
        }
