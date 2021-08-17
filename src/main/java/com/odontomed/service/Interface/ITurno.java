package com.odontomed.service.Interface;

import com.odontomed.dto.response.TurnoResponseDto;
import com.odontomed.model.TurnoPersona;

import java.util.List;

public interface ITurno {

    List<TurnoPersona> getAll();
}
