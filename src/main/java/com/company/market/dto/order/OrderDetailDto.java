package com.company.market.dto.order;

import com.company.market.dto.AddressDto;
import com.company.market.dto.profile.ProfileDetailDto;
import com.company.market.enums.OrderStatus;
import com.company.market.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDetailDto {
    private Integer id;
    private ProfileDetailDto profile;

    private String requirement;
    private String contact;
    private AddressDto address;

    private Double deliveryCost;
    private PaymentType paymentType;
    private OrderStatus status;

    private LocalDateTime deliveryDate;
    private LocalDateTime createdDate;

    private List<OrderItemDetailDto> orderItemList;
}
