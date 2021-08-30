package com.company.market.dto.order;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderItemCreateDto {
    private Integer id;
    @NotNull
    private Integer productId;
    @NotNull
    private Integer amount;
}
