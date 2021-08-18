package com.odontomed.dto.response;

import com.odontomed.model.Turno;
import com.odontomed.model.User;

import java.time.LocalDate;
import java.time.LocalTime;

public interface TurnoSaveResponseDto {

    LocalDate getFecha();
    com.odontomed.model.Turno getTurno();

    interface Turno{
        LocalTime getHs();
    }

}
