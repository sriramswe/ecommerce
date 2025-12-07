package com.backend.ecommerce.Payload.DTO;


import lombok.Data;

@Data
public class BankDetailsDTO {
    private String accountNumber;
    private String UPIId;
    private String accountName;
    private String BankName;
}
