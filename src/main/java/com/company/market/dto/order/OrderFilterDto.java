package com.company.market.dto.order;

import com.company.market.dto.FilterDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderFilterDto extends FilterDto {
    private Integer profileId;
    private Integer productId;
    private String orderDate;

}
