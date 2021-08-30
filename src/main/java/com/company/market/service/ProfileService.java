package com.company.market.service;

import com.company.market.converter.AddressConverter;
import com.company.market.converter.ProfileConverter;
import com.company.market.dto.profile.*;
import com.company.market.entity.AddressEntity;
import com.company.market.entity.ProfileEntity;
import com.company.market.enums.ProfileStatus;
import com.company.market.exception.ProfileNotFoundException;
import com.company.market.exception.ServerBadRequestException;
import com.company.market.repository.AddressRepository;
import com.company.market.repository.ProfileRepository;
import com.company.market.util.JwtTokenUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository repository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private MailSenderService mailSenderService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    // ======*****===== USER ====******=====
    public ProfileDetailDto getDetail(Integer id) {
        return ProfileConverter.toDto(getEntityById(id));
    }

    public void updateUserDetail(Integer id, ProfileUpdateDto dto) {
        ProfileEntity entity = getEntityById(id);
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setContact(dto.getContact());
        repository.save(entity);
    }

    public void updateUserEmail(Integer id, String email) {
        AuthDetails authDetails = new AuthDetails();
        authDetails.setId(id);
        authDetails.setEmail(email);
        String jwt = jwtTokenUtil.generateAccessToken(id, email);
        String link = "http://localhost:8080/profile/verification/" + jwt;
        try {
            mailSenderService.sendEmail(email,
                    "Click the link to confirm your account " + link);
        } catch (Exception e) {
            throw new ServerBadRequestException("Email not delivered");
        }
    }

    public String verification(String jwt) {
        Integer userId = Integer.parseInt(jwtTokenUtil.getUserId(jwt));
        String email = jwtTokenUtil.getUsername(jwt);
        Optional<ProfileEntity> optional = repository.findById(userId);
        if (!optional.isPresent()) {
            throw new ProfileNotFoundException("Profile not found");
        }
        ProfileEntity entity = optional.get();
        entity.setEmail(email);
        return "Successful verified";
    }


    //======================*****=============

    public void create(ProfileCreateDto dto) {
        Optional<ProfileEntity> optional = repository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new ServerBadRequestException("Email already exist");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(DigestUtils.md5Hex(dto.getPassword()));
        entity.setContact(dto.getContact());

        AddressEntity addressEntity = AddressConverter.toEntity(dto.getAddress());
        addressRepository.save(addressEntity);
        entity.setAddress(addressEntity);

        entity.setCreatedDate(LocalDateTime.now());

        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setRole(dto.getRole());

        repository.save(entity);

        ProfileConverter.toDto(entity);
    }

    public ProfileDetailDto getById(Integer id) {
        return ProfileConverter.toDto(getEntityById(id));
    }

    public void update(Integer id, ProfileUpdateDto dto) {
        ProfileEntity entity = getEntityById(id);
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setContact(dto.getContact());
        repository.save(entity);
    }

    public ProfileEntity getEntityById(Integer id) {
        Optional<ProfileEntity> optional = repository.findById(id);
        if (!optional.isPresent()) {
            throw new ServerBadRequestException("Profile not found");
        }
        return optional.get();
    }

    public void changeStatus(Integer id, ProfileStatus status) {
        getEntityById(id);
        repository.changeStatus(status, id);
    }

    public List<ProfileDetailDto> getList() {
        return repository.findAll().stream()
                .map(ProfileConverter::toDto)
                .collect(Collectors.toList());
    }

    public Page<ProfileDetailDto> getPaginationList(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProfileEntity> paging = repository.findAll(pageable);
        return paging.map(ProfileConverter::toDto);
    }

    public Page<ProfileDetailDto> filter(ProfileFilterDto filterDto) {
        String sortBy = filterDto.getSortBy();
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "createdDate";
        }
        Pageable pageable = PageRequest.of(filterDto.getPage(), filterDto.getSize(), filterDto.getDirection(), sortBy);

        List<Predicate> predicateList = new ArrayList<>();
        Specification<ProfileEntity> specification = (root, criteriaQuery, criteriaBuilder) -> {
            if (filterDto.getName() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("name"), filterDto.getName()));
            }
            if (filterDto.getSurname() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("surname"), filterDto.getSurname()));
            }
            if (filterDto.getEmail() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("email"), filterDto.getEmail()));
            }
            if (filterDto.getContact() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("contact"), filterDto.getContact()));
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };
        Page<ProfileEntity> paging = repository.findAll(specification, pageable);
        List<ProfileDetailDto> response = new ArrayList<>();

        paging.forEach(profileEntity -> {
            response.add(ProfileConverter.toDto(profileEntity));
        });
        return paging.map(ProfileConverter::toDto);
    }
}
