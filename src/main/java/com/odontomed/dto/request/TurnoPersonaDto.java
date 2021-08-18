package com.odontomed.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class TurnoPersonaDto {
    @NotBlank(message = "La fecha no puede estar vacío")
    String fecha;

    @NotBlank(message = "El horario no puede estar vacío")
    Long id_horario;
}
