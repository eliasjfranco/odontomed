package com.odontomed.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class UpdatePwdRequestDto {

    @NotBlank(message = "El campo Email no puede estar vacío")
    private String email;

    @NotBlank(message = "El campo Dni no puede estar vacío")
    private String dni;

    @NotBlank(message = "El campo Password no puede estar vacío")
    private String password;
}
