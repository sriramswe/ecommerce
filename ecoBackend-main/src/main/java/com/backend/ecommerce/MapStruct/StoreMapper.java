package com.backend.ecommerce.MapStruct;


import com.backend.ecommerce.Model.Store;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Payload.DTO.StoreDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreMapper {
     public  static StoreDto toDto(Store store){
         StoreDto storeDto = new StoreDto();
         storeDto.setId(store.getId());
         storeDto.setBrand(store.getBrand());
         storeDto.setDescription(store.getDescription());
         storeDto.setStoreAdmin(store.getStoreAdmin());
         storeDto.setStoreType(store.getStoreType());
         storeDto.setStoreContent(store.getContent());
         storeDto.setStatus(store.getStatus());
         storeDto.setUpdateAt(store.getUpdateAt());
           return storeDto;
     }
     public static  Store toEntity(StoreDto storeDto, User user){
         Store store = new Store();
         store.setBrand(storeDto.getBrand());
         store.setStoreType(storeDto.getStoreType());
         store.setStatus(storeDto.getStatus());
         store.setContent(storeDto.getStoreContent());
         store.setUpdateAt(storeDto.getUpdateAt());
         store.setDescription(storeDto.getDescription());
         store.setStoreAdmin(user);
         store.setStatus(storeDto.getStatus());
         return store;
     }
}
