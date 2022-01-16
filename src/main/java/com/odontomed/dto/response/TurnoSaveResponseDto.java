package com.odontomed.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.time.LocalTime;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public interface TurnoSaveResponseDto {

    LocalDate getFecha();

    com.odontomed.model.Turno getTurno();

    interface Turno{
        LocalTime getHs();
    }

}
