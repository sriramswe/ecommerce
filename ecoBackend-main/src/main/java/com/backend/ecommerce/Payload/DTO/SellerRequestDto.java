package com.backend.ecommerce.Payload.DTO;

import com.backend.ecommerce.Model.BankDetails;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
    public class SellerRequestDto {
        @NotBlank(message = "Company name is required")
        @Size(max = 255, message = "Company name cannot exceed 255 characters")
        private String companyName;

        @Size(max = 50, message = "Short name cannot exceed 50 characters")
        private String shortName;

        @NotBlank(message = "Address is required")
        @Size(max = 500, message = "Address cannot exceed 500 characters")
        private String address;

        @NotBlank(message = "City is required")
        @Size(max = 100, message = "City cannot exceed 100 characters")
        private String city;

        @NotBlank(message = "Pin code is required")
        @Pattern(regexp = "^[0-9]{6}$", message = "Pin code must be 6 digits")
        private String pinCode;

        // For registration, we might need user details here or a separate User registration endpoint
        // If the User is created separately, only userId would be passed.
        private Long userId; // Link to an existing User

        // For initial registration, if seller creates user, include password
        @Size(min = 6, message = "Password must be at least 6 characters long")
        private String password; // Only for registration

        private String msmeNumber;

        @NotBlank(message = "GST Number is required")
        @Pattern(regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$", message = "Invalid GST Number format")
        private String gstNumber;

        private BankDetails bankDetails; // Embedded object

        private String photoUrl; // For logo/profile picture

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Size(max = 255, message = "Email cannot exceed 255 characters")
        private String email;
    }

