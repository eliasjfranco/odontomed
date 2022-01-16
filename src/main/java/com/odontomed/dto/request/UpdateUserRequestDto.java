package com.odontomed.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class UpdateUserRequestDto {

    @NotBlank(message = "El campo Telefono no puede estar vacío")
    private String tel;

    @NotBlank(message = "El campo DNI no puede estar vacío")
    private String dni;
}
