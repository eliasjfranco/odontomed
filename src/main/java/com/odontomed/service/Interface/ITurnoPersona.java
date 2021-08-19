package com.odontomed.service.Interface;

import com.odontomed.dto.request.TurnoPersonaDto;
import com.odontomed.dto.response.TurnoResponseDto;
import com.odontomed.dto.response.TurnoSaveResponseDto;
import com.odontomed.exception.InvalidUserException;
import com.odontomed.exception.TurnoAlreadyExists;
import com.odontomed.model.TurnoPersona;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface ITurnoPersona {

    List<TurnoPersona> getAll();

    TurnoSaveResponseDto save(TurnoPersonaDto dto, HttpServletRequest req) throws TurnoAlreadyExists;

    String delete(TurnoPersonaDto dto, HttpServletRequest req) throws InvalidUserException;
}
