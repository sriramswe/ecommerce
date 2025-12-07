package com.backend.ecommerce.MapStruct;

import com.backend.ecommerce.Model.Address;
import com.backend.ecommerce.Payload.DTO.AddressDto;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public static AddressDto toDto(Address address){
        AddressDto addressDto = new
                AddressDto();
        addressDto.setStreetAddress(address.getStreetAddress());
        addressDto.setId(address.getId());
        addressDto.setCity(address.getCity());
        addressDto.setMobile(address.getMobile());
        addressDto.setUser(UserMapper.toDTO(address.getUser()));
        addressDto.setState(address.getState());
        addressDto.setZipcode(address.getZipcode());
        addressDto.setFirstName(address.getFirstName());
        addressDto.setLastName(address.getLastName());
        return  addressDto;
    }
    public static Address toEntity(AddressDto addressDto){
        Address address = new Address();
        address.setCity(addressDto.getCity());
        address.setId(addressDto.getId());
        address.setMobile(addressDto.getMobile());
        address.setState(addressDto.getState());
        address.setLastName(addressDto.getLastName());
        address.setUser(UserMapper.toEntity(addressDto.getUser()));
        address.setZipcode(addressDto.getZipcode());
        address.setStreetAddress(addressDto.getStreetAddress());
        address.setFirstName(addressDto.getFirstName());
        return address;

    }

}
