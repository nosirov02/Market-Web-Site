package com.company.market.dto.product;

import com.company.market.enums.ProductType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class ProductCreateDto {
    @NotBlank(message = ("Invalid name"))
    private String name;
    @NotEmpty(message = ("Can't be empty"))
    private String description;
    @NotEmpty
    private String descriptionUz;
    @NotEmpty
    private String descriptionRu;
    @NotBlank(message = ("Invalid price"))
    private Double price;
    @NotBlank
    private ProductType type;

}
