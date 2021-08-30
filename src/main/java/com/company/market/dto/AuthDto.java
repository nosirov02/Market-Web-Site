package com.company.market.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class AuthDto {
    @NotBlank(message = ("Email error"))
    private String email;
    @NotBlank(message = ("Password error"))
    @Size(min = 8, message = ("Password must be less than 8 length"))
    private String password;
}
