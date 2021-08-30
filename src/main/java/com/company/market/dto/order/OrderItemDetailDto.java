package com.company.market.dto.order;

import com.company.market.dto.product.ProductDetailDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderItemDetailDto {
    private Integer id;
    private Integer orderId;
    private ProductDetailDto product;
    private Integer amount;
    private LocalDateTime createdDate;
    private Double price;
}
