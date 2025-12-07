package com.backend.ecommerce.Model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class BankDetails {
    private  String accoutnNumber;
    private String upiId;
    private String accountName;
    private String bankName;
}
