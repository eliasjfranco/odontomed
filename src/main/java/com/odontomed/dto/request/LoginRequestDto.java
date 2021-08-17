package com.odontomed.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Setter @NoArgsConstructor
public class LoginRequestDto {

    @Email
    @NotBlank(message = "El campo Email no puede estar vacio")
    private String email;

    @NotBlank(message = "El campo Password no puede estar vacio")
    private String password;
}
