package com.company.market.dto.profile;

import com.company.market.dto.AddressDto;
import com.company.market.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ProfileCreateDto {
    private Integer id;
    @NotBlank(message = ("Please provide a name"))
    private String name;
    @NotEmpty(message = ("Please provide a surname"))
    private String surname;
    @Email(message = ("Please provide an email"))
    private String email;
    @NotEmpty(message = ("Please provide contact"))
    private String contact;
    @NotBlank(message = ("Please provide a password"))
    @Size(min = 8, message = "Password must be high than 8")
    private String password;
    @NotEmpty(message = ("Image id error"))
    private Integer imageId;
    @NotNull
    private ProfileRole role;
    @NotNull
    private AddressDto address;
}
