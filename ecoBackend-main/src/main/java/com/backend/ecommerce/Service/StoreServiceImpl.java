package com.backend.ecommerce.Service;

import com.backend.ecommerce.Exception.UserException;
import com.backend.ecommerce.MapStruct.StoreMapper;
import com.backend.ecommerce.Model.*;
import com.backend.ecommerce.Payload.DTO.StoreDto;
import com.backend.ecommerce.Repository.StoreRepository;
import com.backend.ecommerce.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private  final UserService userService;
    private final UserRepository userRepository;

    @Override
    public StoreDto createStore(StoreDto storeDto, User user) throws UserException {
        if(user.getRoles()!= UserRole.ROLE_SELLER &&
           user.getRoles()!=UserRole.ROLE_ADMIN
        ){
            throw  new UserException("ONLY SELLER or ADMIN can Create a Store");
        }
        if(user.getStore()!=null){
            throw new UserException("You already own a Store");
        }
        Store store = StoreMapper.toEntity(storeDto, user);
        store.setStoreAdmin(user);
        store.setStatus(StoreStatus.PENDING);
        return StoreMapper.toDto(storeRepository.save(store));
    }

    @Override
    public StoreDto GetStoreById(Long Id) throws Exception {
        Store store = storeRepository.findById(Id)
                .orElseThrow(()-> new Exception("Store Not Found"));
        return StoreMapper.toDto(store);
    }

    @Override
    public List<StoreDto> getAllStores() {
        return storeRepository.findAll()
                .stream()
                .map(StoreMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public StoreDto getStoreByAdmin() throws UserException, Exception {
        User admin  = userService.getCurrentUser();
        if(admin.getRoles()!= UserRole.ROLE_ADMIN
        && admin.getRoles()!= UserRole.ROLE_SELLER
        ){
            throw  new UserException("you don't have permission");
        }
        Store store  = storeRepository.findByStoreAdmin_Id(admin.getId());

        if(store == null){
            throw new Exception("Store not Found");
        }

        return StoreMapper.toDto(store);
    }

    @Override
    public void Delete(Long Id) throws UserException, Exception {
        User user = userService.getCurrentUser();
        Store store = storeRepository.findById(user.getId())
                .orElseThrow(()-> new Exception("Store not Found"));
        if(user.getRoles() != UserRole.ROLE_ADMIN && !store.getStoreAdmin().getId().equals(user.getId())){
            throw new UserException("you cannot delete this Store");

        }
        storeRepository.delete(store);
    }

    @Override
    public StoreDto UpdateStore(Long Id, StoreDto storeDto) throws Exception, UserException {
               User user = userService.getCurrentUser();
               Store existing = storeRepository.findById(Id).orElseThrow(()-> new Exception("Store Not Found"));

               if(!existing.getStoreAdmin().getId().equals(user.getId())&& user.getRoles()!= UserRole.ROLE_ADMIN){
                   throw new UserException("unauthorized to update this Store");
               }
               existing.setBrand(storeDto.getBrand());
               existing.setStoreType(storeDto.getStoreType());
               existing.setUpdateAt(storeDto.getUpdateAt());

               StoreContent storeContent = StoreContent.builder()
                       .address(storeDto.getStoreContent().getAddress())
                       .phone(storeDto.getStoreContent().getPhone())
                       .email(storeDto.getStoreContent().getEmail())
                       .build();
               existing.setContent(storeContent);
               return StoreMapper.toDto(storeRepository.save(existing));
    }

    @Override
    public StoreDto getStoreByEmployeeId(Long userId) throws UserException {
                        User employee = userService.getCurrentUser();
                        if (employee.getRoles()!= UserRole.ROLE_EMPLOYEE &&
                           employee.getRoles()!= UserRole.ROLE_ADMIN
                        ){
                            throw new UserException("you not have permission");
                        }
        User targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found"));

        Store store = targetUser.getStore();

        if (store == null) {
            throw new UserException("This employee is not assigned to any store");
        }

        return StoreMapper.toDto(store);
    }

    @Override
    public StoreDto moderateStore(Long Id, StoreStatus status) throws Exception, UserException {
        User admin = userService.getCurrentUser();
        if(admin.getRoles()!= UserRole.ROLE_ADMIN){
            throw new UserException("ONly Admin can moderate Stores");
        }
        Store store = storeRepository.findById(Id).orElseThrow(()-> new Exception("Store not Found"));

        store.setStatus(status);
        return StoreMapper.toDto(storeRepository.save(store));
    }
}
