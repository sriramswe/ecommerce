package com.backend.ecommerce.Service;

import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.Model.Product;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Payload.DTO.ProductDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    // Accept a ProductDto when creating a product
    ProductDto createProduct(ProductDto productDto, User userDto) throws UserException, Exception;
    ProductDto updateProduct(ProductDto productDto,Long Id, User userDto) throws UserException,Exception;
    void DeleteProduct(Long id,User user)throws Exception,UserException;
    List<ProductDto> findProductByCategory(String category);
    Page<Product> getAllProduct(String category,List<String>color , List<String> sizes,Integer minPrice , Integer maxPrice
    ,Integer MinDiscount, String sort, String Stock,Integer pageNumber,Integer PageSize);

    Product findProductById(Long Id) throws Exception;
}
