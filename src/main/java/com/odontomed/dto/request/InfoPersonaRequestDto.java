package com.odontomed.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class InfoPersonaRequestDto {

    @NotBlank(message = "El campo Email no puede estar vacío")
    private String email;
}
