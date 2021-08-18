package com.odontomed.service.Impl;

import com.odontomed.dto.request.TurnoPersonaDto;
import com.odontomed.dto.response.TurnoSaveResponseDto;
import com.odontomed.exception.TurnoAlreadyExists;
import com.odontomed.jwt.JwtEntryPoint;
import com.odontomed.jwt.JwtProvider;
import com.odontomed.jwt.JwtTokenFilter;
import com.odontomed.model.Turno;
import com.odontomed.model.TurnoPersona;
import com.odontomed.model.User;
import com.odontomed.repository.TurnoPersonaRepository;
import com.odontomed.repository.TurnoRepository;
import com.odontomed.repository.UserRepository;
import com.odontomed.service.Interface.ITurnoPersona;
import com.odontomed.util.FormatDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Service
public class TurnoPersonaServiceImpl implements ITurnoPersona {

    @Autowired
    TurnoPersonaRepository repository;
    @Autowired
    ProjectionFactory projectionFactory;
    @Autowired
    MessageSource messageSource;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    JwtTokenFilter jwtFilter;
    @Autowired
    FormatDate formatDate;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TurnoRepository turnoRepository;

    @Override
    public List<TurnoPersona> getAll() {
        LocalDate date = LocalDate.now();
        date = formatDate.dateToDate(date);
        System.out.println(date);
        return repository.obtenerAllByFecha(date);
    }

    @Override
    public TurnoSaveResponseDto save(TurnoPersonaDto dto, HttpServletRequest req) throws TurnoAlreadyExists {
        if(repository.getByFechaAndId(formatDate.stringToDate(dto.getFecha()), dto.getId_horario()).isPresent())
            throw new TurnoAlreadyExists(messageSource.getMessage("turno.error.exists",null, Locale.getDefault()));

        String token = jwtFilter.getToken(req);
        String username = jwtProvider.getNombreUsuarioFromToken(token);
        User user = userRepository.findByEmail(username).get();
        TurnoPersona turnoPersona = new TurnoPersona();
        if(turnoRepository.findById(dto.getId_horario()).isPresent())
            turnoPersona.setTurno(turnoRepository.findById(dto.getId_horario()).get());
        turnoPersona.setId_horario(dto.getId_horario());

        turnoPersona.setUser(user);
        turnoPersona.setFecha(formatDate.stringToDate(dto.getFecha()));

        return projectionFactory.createProjection(TurnoSaveResponseDto.class, repository.save(turnoPersona));
    }
}
