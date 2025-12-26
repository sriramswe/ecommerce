package com.backend.ecommerce.Payload.DTO;

import com.backend.ecommerce.Model.BankDetails;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SellerDTO {
    private Long id;

    private String companyName;


    private String shortName;

    private String address;


    private String city;



    private String pinCode;


    private Long userId; // Link to an existing User

    private String password; // Only for registration

    private String msmeNumber;

    private String gstNumber;

    private BankDetailsDTO bankDetails; // Embedded object

    private String photoUrl; // For logo/profile picture


    private String email;
}