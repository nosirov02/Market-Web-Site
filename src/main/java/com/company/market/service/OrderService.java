package com.company.market.service;

import com.company.market.converter.AddressConverter;
import com.company.market.dto.AddressDto;
import com.company.market.dto.order.*;
import com.company.market.dto.product.ProductDetailDto;
import com.company.market.dto.profile.ProfileDetailDto;
import com.company.market.entity.AddressEntity;
import com.company.market.entity.OrderEntity;
import com.company.market.entity.OrderItemEntity;
import com.company.market.entity.ProfileEntity;
import com.company.market.enums.OrderStatus;
import com.company.market.exception.ItemNotFoundException;
import com.company.market.exception.ServerBadRequestException;
import com.company.market.repository.AddressRepository;
import com.company.market.repository.OrderItemRepository;
import com.company.market.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ProductService productService;

    public OrderDetailDto create(Integer profileId, OrderCreateDto dto) {
        OrderEntity order = new OrderEntity();
        order.setProfileId(profileId);
        order.setRequirement(dto.getRequirement());
        order.setContact(dto.getContact());
        order.setPaymentType(dto.getPaymentType());
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedDate(LocalDateTime.now());

        AddressEntity addressEntity = new AddressEntity();
        AddressDto addressDto = new AddressDto();
        addressEntity.setDistrict(addressDto.getDistrict());
        addressEntity.setStreet(addressDto.getStreet());
        addressEntity.setHome(addressDto.getHome());
        addressRepository.save(addressEntity);
        addressDto.setId(addressEntity.getId());
        order.setAddress(addressEntity);

        repository.save(order);

        dto.getItemList().forEach(orderItem -> {
            createNewOrderItem(orderItem, order.getId());
        });
        return createOrderDetailDto(order);
    }

    public OrderDetailDto update(Integer profileId, Integer orderId, OrderCreateDto dto) {
        OrderEntity entity = getEntity(orderId);
        if (!entity.getProfileId().equals(profileId)) {
            throw new ServerBadRequestException("Not Belongs to Profile");
        }
        if (!entity.getStatus().equals(OrderStatus.CREATED)) {
            throw new ServerBadRequestException("Order In Wrong Status");
        }

        entity.setProfileId(profileId);
        entity.setRequirement(dto.getRequirement());
        entity.setAddress(AddressConverter.toEntity(dto.getAddressDto()));
        entity.setContact(dto.getContact());
        entity.setPaymentType(dto.getPaymentType());
        entity.setCreatedDate(LocalDateTime.now());

        repository.save(entity);

        List<OrderItemEntity> oldList = orderItemRepository.findAllByOrderId(entity.getId());
        List<OrderItemCreateDto> newList = dto.getItemList();

        newList.forEach(item -> {
            if (item.getId() == null) {
                createNewOrderItem(item, entity.getId());
            } else {
                updateNewOrderItem(item);
            }
        });

        oldList.forEach(oldItem -> {
            if (!newList.stream().anyMatch(newItem -> newItem.getId() != null && oldItem.getId().equals(newItem.getId()))) {
                orderItemRepository.delete(oldItem);
            }
        });
        return createOrderDetailDto(entity);
    }

    public OrderDetailDto getById(Integer productId) {
        OrderEntity entity = getEntity(productId);
        return createOrderDetailDto(entity);
    }

    public Page<OrderDetailDto> filter(OrderFilterDto filterDto) {
        String sortBy = filterDto.getSortBy();
        Sort.Direction direction = filterDto.getDirection();
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "createdDate";
            direction = Sort.Direction.DESC;
        }
        Pageable pageable = PageRequest.of(filterDto.getPage(), filterDto.getSize(), direction, sortBy);

        List<Predicate> predicateList = new ArrayList<>();
        Specification<OrderEntity> specification = (root, criteriaQuery, criteriaBuilder) -> {
            if (filterDto.getOrderDate() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
                LocalDateTime fromDate = LocalDate.parse(filterDto.getOrderDate(), formatter).atStartOfDay();
                LocalDateTime toDate = fromDate.plusDays(1);

                predicateList.add(criteriaBuilder.between(root.get("createdDate"), fromDate, toDate));
            }
            if (filterDto.getProductId() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("productId"), filterDto.getProductId()));
            }
            if (filterDto.getProfileId() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("profileId"), filterDto.getProfileId()));
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };
        Page<OrderEntity> paging = repository.findAll(specification, pageable);

        Page<OrderDetailDto> resultPaging = paging.map(orderEntity -> toDto(orderEntity));
        return resultPaging;
    }

    public OrderEntity getEntity(Integer productId) {
        Optional<OrderEntity> optional = repository.findById(productId);
        if (!optional.isPresent()) {
            throw new ItemNotFoundException("Order not found");
        }
        return optional.get();
    }

    private OrderItemEntity createNewOrderItem(OrderItemCreateDto dto, Integer orderId) {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setOrderId(orderId);
        orderItemEntity.setProductId(dto.getProductId());
        orderItemEntity.setAmount(dto.getAmount());
        orderItemEntity.setPrice(productService.findEntityById(dto.getProductId()).getPrice());
        orderItemEntity.setCreatedDate(LocalDateTime.now());
        orderItemRepository.save(orderItemEntity);
        return orderItemEntity;
    }

    private OrderDetailDto createOrderDetailDto(OrderEntity entity) {
        OrderDetailDto detailDto = toDto(entity);
        List<OrderItemEntity> orderItemList = orderItemRepository.findAllByOrderId(detailDto.getId());
        List<OrderItemDetailDto> orderItemDtoList = orderItemList.stream().map(item -> toDto(item)).collect(Collectors.toList());
        detailDto.setOrderItemList(orderItemDtoList);
        return detailDto;
    }

    private void updateNewOrderItem(OrderItemCreateDto order) {
        Optional<OrderItemEntity> optional = orderItemRepository.findById(order.getId());
        if (!optional.isPresent()) {
            throw new ItemNotFoundException("Order item not found");
        }
        OrderItemEntity entity = optional.get();
        entity.setAmount(order.getAmount());
        entity.setProductId(order.getProductId());
        entity.setPrice(productService.findEntityById(order.getProductId()).getPrice());
        orderItemRepository.save(entity);
    }

    public OrderDetailDto toDto(OrderEntity orderEntity) {
        OrderDetailDto order = new OrderDetailDto();
        order.setId(orderEntity.getId());
        order.setProfile(profileService.getDetail(orderEntity.getProfileId()));
        order.setRequirement(orderEntity.getRequirement());
        order.setAddress(AddressConverter.toDto(orderEntity.getAddress()));
        order.setContact(orderEntity.getContact());
        order.setDeliveryCost(order.getDeliveryCost());
        order.setPaymentType(orderEntity.getPaymentType());
        order.setStatus(orderEntity.getStatus());
        order.setCreatedDate(LocalDateTime.now());
        return order;
    }

    public OrderItemDetailDto toDto(OrderItemEntity itemEntity) {
        OrderItemDetailDto dto = new OrderItemDetailDto();
        dto.setId(itemEntity.getId());
        dto.setCreatedDate(itemEntity.getCreatedDate());
        dto.setAmount(itemEntity.getAmount());
        dto.setOrderId(itemEntity.getOrderId());
        dto.setProduct(productService.getById(itemEntity.getProductId(), true));
        dto.setPrice(itemEntity.getPrice());
        return dto;
    }
}
