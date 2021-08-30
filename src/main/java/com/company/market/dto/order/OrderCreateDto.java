package com.company.market.dto.order;

import com.company.market.dto.AddressDto;
import com.company.market.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class OrderCreateDto {
    private Integer id;
    private String requirement;
    private AddressDto addressDto;
    @NotBlank
    private String contact;
    @NotNull
    private PaymentType paymentType;
    @Size(min = 1)
    private List<OrderItemCreateDto> itemList;
}
