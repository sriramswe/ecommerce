package com.backend.ecommerce.Payload.DTO;


import com.backend.ecommerce.Model.Product;
import com.backend.ecommerce.Model.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class WatchListItemsDto {
    private Long Id;
    private User user;
    private Product product;
}
