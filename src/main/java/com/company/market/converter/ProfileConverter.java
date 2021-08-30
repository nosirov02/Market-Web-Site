package com.company.market.converter;

import com.company.market.dto.profile.ProfileDetailDto;
import com.company.market.entity.ProfileEntity;

public class ProfileConverter {

    public static ProfileDetailDto toDto(ProfileEntity entity) {
        if (entity == null)
            return null;

        ProfileDetailDto dto = new ProfileDetailDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setContact(entity.getContact());
        dto.setStatus(entity.getStatus());
        dto.setRole(entity.getRole());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public static ProfileDetailDto toShortDetail(ProfileEntity entity) {
        if (entity == null)
            return null;
        ProfileDetailDto dto = new ProfileDetailDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setContact(entity.getContact());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
