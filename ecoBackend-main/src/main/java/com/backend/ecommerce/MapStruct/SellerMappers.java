package com.backend.ecommerce.MapStruct;

import com.backend.ecommerce.Model.BankDetails;
import com.backend.ecommerce.Model.Seller;
import com.backend.ecommerce.Payload.DTO.BankDetailsDTO;
import com.backend.ecommerce.Payload.DTO.SellerDTO;
import com.backend.ecommerce.Payload.DTO.SellerRequestDto;
import org.springframework.stereotype.Component;

@Component
public class SellerMappers {

    /* =====================================================
       ENTITY → DTO
    ====================================================== */
    public SellerDTO toDto(Seller seller) {

        if (seller == null) return null;

        SellerDTO dto = new SellerDTO();

        dto.setId(seller.getId());
        dto.setCompanyName(seller.getCompanyName());
        dto.setShortName(seller.getShortName());
        dto.setAddress(seller.getAddress());
        dto.setCity(seller.getCity());
        dto.setPinCode(seller.getPinCode());
        dto.setEmail(seller.getEmail());
        dto.setGstNumber(seller.getGstNumber());
        dto.setMsmeNumber(seller.getMsmeNumber());
        dto.setPhotoUrl(seller.getPhotoUrl());

        if (seller.getUser() != null) {
            dto.setUserId(seller.getUser().getId());
        }

        dto.setBankDetails(toBankDto(seller.getBankDetails()));

        return dto;
    }

    /* =====================================================
       DTO → ENTITY (CREATE)
    ====================================================== */
    public Seller toEntity(SellerRequestDto dto) {

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

    /* =====================================================
       DTO → EXISTING ENTITY (UPDATE)
    ====================================================== */
    public void updateEntity(SellerRequestDto dto, Seller seller) {

        if (dto == null || seller == null) return;

        if (dto.getCompanyName()!= null) seller.setCompanyName(dto.getCompanyName());
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

            BankDetails bd =  new BankDetails();

            if (bd.getAccoutnNumber() != null)
                bank.setAccoutnNumber(bd.getAccoutnNumber());

            if (bd.getUpiId() != null)
                bank.setUpiId(bd.getUpiId());

            if (bd.getAccountName() != null)
                bank.setAccountName(bd.getAccountName());

            if (bd.getBankName() != null)
                bank.setBankName(bd.getBankName());

            seller.setBankDetails(bank);
        }
    }

    /* =====================================================
       HELPER: BankDetails → DTO
    ====================================================== */
    private BankDetailsDTO toBankDto(BankDetails bank) {

        if (bank == null) return null;

        BankDetailsDTO dto = new BankDetailsDTO();
        dto.setAccountNumber(bank.getAccoutnNumber());
        dto.setUPIId(bank.getUpiId());
        dto.setAccountName(bank.getAccountName());
        dto.setBankName(bank.getBankName());

        return dto;
    }

    /* =====================================================
       HELPER: DTO → BankDetails
    ====================================================== */
    private BankDetails toBankEntity(BankDetailsDTO dto) {

        if (dto == null) return null;

        BankDetails bank = new BankDetails();
        bank.setAccoutnNumber(dto.getAccountNumber());
        bank.setUpiId(dto.getUPIId());
        bank.setAccountName(dto.getAccountName());
        bank.setBankName(dto.getBankName());

        return bank;
    }
}
