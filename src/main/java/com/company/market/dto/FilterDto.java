package com.company.market.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Setter
@Getter
public class FilterDto {
    private Integer page;
    private Integer size;
    private String sortBy;
    private Sort.Direction direction;
}
