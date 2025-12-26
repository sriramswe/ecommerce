package com.backend.ecommerce.Service;

import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.MapStruct.ProductMapper;
import com.backend.ecommerce.Model.Category;
import com.backend.ecommerce.Model.Product;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Payload.DTO.ProductDto;
import com.backend.ecommerce.Repository.CategoryRepository;
import com.backend.ecommerce.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
   private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public ProductDto createProduct(ProductDto productDto, User userDto) throws Exception {



        Category topCategory = categoryRepository.findByName(productDto.getTopLevelCategory());
        if (topCategory == null) {
            topCategory = new Category();
            topCategory.setName(productDto.getTopLevelCategory());
            topCategory.setLevel(1);
            topCategory = categoryRepository.save(topCategory);
        }

        // 2️⃣ Find or create second-level category
        Category secondLevel = categoryRepository.findByNameAndParentCategory(productDto.getSecondLevelCategory(), topCategory);
        if (secondLevel == null) {
            secondLevel = new Category();
            secondLevel.setName(productDto.getSecondLevelCategory());
            secondLevel.setParentCategory(topCategory);
            secondLevel.setLevel(2);
            secondLevel = categoryRepository.save(secondLevel);
        }

        // 3️⃣ Find or create third-level category
        Category thirdLevel = categoryRepository.findByNameAndParentCategory(productDto.getThirdlevelCategory(), secondLevel);
        if (thirdLevel == null) {
            thirdLevel = new Category();
            thirdLevel.setName(productDto.getThirdlevelCategory());
            thirdLevel.setParentCategory(secondLevel);
            thirdLevel.setLevel(3);
            thirdLevel = categoryRepository.save(thirdLevel);
        }

        // 4️⃣ Map DTO to Entity and save
        Product product = ProductMapper.toEntity(productDto);
        product.setCreated_At(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        // 5️⃣ Return DTO
        return ProductMapper.toDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto,Long Id ,User userDto) throws UserException, Exception {
       Product product = productRepository.findById(Id).orElseThrow(
               ()-> new Exception("Product not found")
       );
       product.setTitle(productDto.getTitle());
       product.setRatings(productDto.getRatings());
       product.setNumRatings(productDto.getNumRatings());
       product.setReviews(productDto.getReviews());
       product.setColor(productDto.getColor());
       product.setDiscountedPrice(productDto.getDiscountedPrice());
       product.setDoublePercent(productDto.getDoublePercent());
       product.setQuantity(productDto.getQuantity());
       product.setDescription(productDto.getDescription());
       product.setCreated_At(productDto.getCreated_At());
       if(productDto.getCategory()!= null){
           Category category = categoryRepository.findById(productDto.getCategory().getId())
                   .orElseThrow(
                           ()-> new Exception("Category not found")
                   );
           product.setCategory(category);
       }
       product.setImageUrl(productDto.getImageUrl());
       Product savedProduct = productRepository.save(product);
       return ProductMapper.toDto(savedProduct);
    }

    @Override
    public void DeleteProduct(Long id, User user) throws Exception, UserException {
           Product product = productRepository.findById(id).orElseThrow(
                   () -> new Exception("Product not found")
           );
           product.getSizes().clear();
           productRepository.delete(product);
    }


    @Override
    public List<ProductDto> findProductByCategory(String category) {
         List<Product> products = productRepository.findByCategory_Name(category);


        return products.stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> color, List<String> sizes, Integer minPrice, Integer maxPrice, Integer MinDiscount, String sort, String Stock, Integer pageNumber, Integer PageSize) {
        Pageable pageable = PageRequest.of(pageNumber,PageSize, Sort.by(sort));
       List<Product> products = productRepository.filterProduct(category,minPrice,maxPrice, Integer.valueOf(sort),MinDiscount);
        if(!color.isEmpty()){
            products = products.stream().filter(p->color.stream().anyMatch(c-> c.equalsIgnoreCase(p.getColor()))).collect(
                    Collectors.toList()
            );
        }
        if(Stock!=null){
            if(Stock.equalsIgnoreCase("inStock")){
                products = products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
            }
            if (Stock.equalsIgnoreCase("Out of Stock")){
                products = products.stream().filter(p->p.getQuantity()<=0).collect(Collectors.toList());
            }
        }
            int startIndex = (int) pageable.getOffset();
          int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());
          List<Product> pageContent = products.subList(Math.max(0, startIndex), Math.max(Math.min(endIndex, products.size()), 0));
          return new PageImpl<>(pageContent, pageable, products.size());
     }

     @Override
     public Product findProductById(Long Id) throws Exception {
         Optional<Product> opt = productRepository.findById(Id);
         if (opt.isPresent()){
             return opt.get();
         }
         throw new Exception("Product not found with id="+Id);

     }
}
