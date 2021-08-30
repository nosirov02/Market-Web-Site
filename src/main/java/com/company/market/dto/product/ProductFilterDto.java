package com.company.market.dto.product;

import com.company.market.dto.FilterDto;
import com.company.market.enums.ProductType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductFilterDto extends FilterDto {
    private String name;
    private Double minPrice;
    private Double maxPrice;
    private Integer rate;
    private ProductType type;
    private List<String> nameList;
    private List<ProductType> typeList;
}
