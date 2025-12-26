package com.backend.ecommerce.Payload.DTO;

import com.backend.ecommerce.Model.StoreContent;
import com.backend.ecommerce.Model.StoreStatus;
import com.backend.ecommerce.Model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto {
    private Long Id;
    private String brand;
    private User storeAdmin;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String description;
    private String storeType;
    private StoreStatus status;
    private StoreContent storeContent;
}





