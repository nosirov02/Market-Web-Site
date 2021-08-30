package com.company.market.dto.profile;

import com.company.market.dto.AddressDto;
import com.company.market.enums.ProfileRole;
import com.company.market.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileDetailDto {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String contact;
    private ProfileStatus status;
    private String password;
    private Integer imageId;
    private ProfileRole role;
    private LocalDateTime createdDate;
    private String token;
    private AddressDto address;
}
