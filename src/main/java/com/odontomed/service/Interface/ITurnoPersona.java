package com.odontomed.service.Interface;

import com.amazonaws.services.xray.model.Http;
import com.odontomed.dto.request.TurnoPersonaDto;
import com.odontomed.dto.response.TurnoResponseDto;
import com.odontomed.dto.response.TurnoSaveResponseDto;
import com.odontomed.exception.InvalidUserException;
import com.odontomed.exception.TurnoAlreadyExists;
import com.odontomed.exception.TurnoNotFoundException;
import com.odontomed.model.Jwt;
import com.odontomed.model.Turno;
import com.odontomed.model.TurnoPersona;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ITurnoPersona {

    List<TurnoPersona> getAll();

    TurnoSaveResponseDto save(TurnoPersonaDto dto, HttpServletRequest req) throws TurnoAlreadyExists, TurnoNotFoundException;

    String delete(TurnoPersonaDto dto, HttpServletRequest req) throws TurnoNotFoundException, InvalidUserException;

    TurnoSaveResponseDto update(TurnoPersonaDto dto, HttpServletRequest req) throws TurnoAlreadyExists, TurnoNotFoundException;

    List<Turno> getAllId();

    Integer getCantId();

    String getInfo(HttpServletRequest req, Jwt jwt);
}
