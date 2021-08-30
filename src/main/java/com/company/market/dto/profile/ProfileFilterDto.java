package com.company.market.dto.profile;

import com.company.market.dto.FilterDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProfileFilterDto extends FilterDto {
    private String name;
    private String surname;
    private String email;
    private String contact;
    private List<String> nameList;
}
