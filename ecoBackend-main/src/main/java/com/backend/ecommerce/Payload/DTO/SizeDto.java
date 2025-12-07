package com.backend.ecommerce.Payload.DTO;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class SizeDto {
        private String name;
        private int quantity;

}
