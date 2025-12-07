package com.backend.ecommerce.Service;

import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.Model.StoreStatus;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Payload.DTO.StoreDto;

import java.util.List;

public interface StoreService {
    StoreDto createStore(StoreDto storeDto , User user) throws UserException;
     StoreDto GetStoreById(Long Id) throws Exception;
     List<StoreDto> getAllStores();
     StoreDto getStoreByAdmin() throws UserException, Exception;
     void Delete(Long Id) throws UserException, Exception;
     StoreDto UpdateStore(Long Id, StoreDto storeDto) throws  Exception,UserException;
   StoreDto getStoreByEmployeeId(Long userId) throws UserException;
   StoreDto moderateStore(Long Id , StoreStatus status) throws Exception, UserException;

}
