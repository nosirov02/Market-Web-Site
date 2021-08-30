package com.company.market.converter;

import com.company.market.dto.AddressDto;
import com.company.market.entity.AddressEntity;

public class AddressConverter {
    public static AddressEntity toEntity(AddressDto dto){
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setDistrict(dto.getDistrict());
        addressEntity.setStreet(dto.getStreet());
        addressEntity.setHome(dto.getHome());
        return addressEntity;
    }

    public static AddressDto toDto(AddressEntity address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setDistrict(address.getDistrict());
        addressDto.setStreet(address.getStreet());
        addressDto.setHome(address.getHome());
        return addressDto;
    }
}
