package com.backend.ecommerce.Payload.DTO;

import com.backend.ecommerce.Model.PaymentInformation;
import com.backend.ecommerce.Model.Rating;
import com.backend.ecommerce.Model.Review;
import com.backend.ecommerce.Model.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto  {
    private Long id;
    private String firstName;
    private String lastName;
    private String Password;
    private String email;
    private String mobile;
    private String role;
    private Store storeDto;
 private List<AddressDto> address = new ArrayList<>();

private List<PaymentInformation> paymentInformation = new ArrayList<>();

private List<Rating> ratings = new ArrayList<>();

private List<Review> reviews = new ArrayList<>();

 private LocalDateTime createAt;

}
