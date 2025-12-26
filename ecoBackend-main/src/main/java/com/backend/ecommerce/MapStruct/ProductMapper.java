package com.backend.ecommerce.MapStruct;

import com.backend.ecommerce.Model.Product;
import com.backend.ecommerce.Payload.DTO.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public static ProductDto toDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setCategory(product.getCategory());
        productDto.setBrand(product.getBrand());
        productDto.setColor(product.getColor());
        productDto.setDescription(product.getDescription());
        productDto.setSizes(product.getSizes());
        productDto.setPrice(product.getPrice());
        productDto.setCreated_At(product.getCreated_At());
        productDto.setQuantity(product.getQuantity());
        productDto.setDiscountedPrice(product.getDiscountedPrice());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setReviews(product.getReviews());
        productDto.setTitle(product.getTitle());
        productDto.setNumRatings(product.getNumRatings());
        productDto.setRatings(product.getRatings());
        productDto.setSellerDTO(product.getSeller());

        return productDto;
    }
    public static Product toEntity(ProductDto productDto){
        Product product = new Product();
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCategory());
        product.setDescription(productDto.getDescription());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setImageUrl(productDto.getImageUrl());
        product.setColor(productDto.getColor());
        product.setCreated_At(productDto.getCreated_At());
        product.setReviews(productDto.getReviews());
        product.setTitle(productDto.getTitle());
        product.setNumRatings(productDto.getNumRatings());
        product.setRatings(productDto.getRatings());
        product.setPrice(productDto.getPrice());
        product.setSeller(productDto.getSellerDTO());

        return product;
    }
    public static Product updateEntity(ProductDto productDto , Product product ){
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCategory());
        product.setDescription(productDto.getDescription());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setImageUrl(productDto.getImageUrl());
        product.setColor(productDto.getColor());
        product.setCreated_At(productDto.getCreated_At());
        product.setReviews(productDto.getReviews());
        product.setTitle(productDto.getTitle());
        product.setNumRatings(productDto.getNumRatings());
        product.setRatings(productDto.getRatings());
        product.setPrice(productDto.getPrice());
        product.setSeller(productDto.getSellerDTO());
        return product;
    }


}
