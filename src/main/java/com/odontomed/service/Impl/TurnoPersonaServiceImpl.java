package com.odontomed.service.Impl;

import com.odontomed.config.SendgridConfig;
import com.odontomed.dto.request.TurnoPersonaDto;
import com.odontomed.dto.response.TurnoResponseDto;
import com.odontomed.dto.response.TurnoSaveResponseDto;
import com.odontomed.exception.InvalidUserException;
import com.odontomed.exception.TurnoAlreadyExists;
import com.odontomed.exception.TurnoNotFoundException;
import com.odontomed.jwt.JwtEntryPoint;
import com.odontomed.jwt.JwtProvider;
import com.odontomed.jwt.JwtTokenFilter;
import com.odontomed.model.*;
import com.odontomed.repository.TurnoPersonaRepository;
import com.odontomed.repository.TurnoRepository;
import com.odontomed.repository.UserRepository;
import com.odontomed.service.Interface.ITurnoPersona;
import com.odontomed.util.FormatDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.function.LongToIntFunction;

@Service
public class TurnoPersonaServiceImpl implements ITurnoPersona {

    @Autowired
    TurnoPersonaRepository repository;
    @Autowired
    ProjectionFactory projectionFactory;
    @Autowired
    MessageSource messageSource;
    @Autowired
    FormatDate formatDate;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TurnoRepository turnoRepository;
    @Autowired
    SendgridConfig sendgrid;
    @Autowired
    JwtTokenFilter jwtFilter;
    @Autowired
    JwtProvider jwtProvider;

    @Override
    public List<TurnoPersona> getAll() {
        LocalDate date = LocalDate.now();
        date = formatDate.dateToDate(date);
        return repository.obtenerAllByFecha(date);
    }

    @Override
    public TurnoSaveResponseDto save(TurnoPersonaDto dto, HttpServletRequest req) throws TurnoAlreadyExists, TurnoNotFoundException {
        //Confirma que no este agendado el turno con fecha y horario
        if(existTurno(dto))
            throw new TurnoAlreadyExists(messageSource.getMessage("turno.error.exists",null, Locale.getDefault()));

        //Confirma que el turno a agendar, es fecha posterior al dia de hoy.
        if(compareTurno(dto.getFecha()))
            throw new TurnoNotFoundException(messageSource.getMessage("turno.error.save", null, Locale.getDefault()));
        //Obtenemos usuario en base al token
        User user = getUserByToken(req);
        TurnoPersona turnoPersona = new TurnoPersona();

        if(turnoRepository.findById(dto.getId_horario()).isPresent()){
            turnoPersona.setTurno(turnoRepository.findById(dto.getId_horario()).get());
        }
        else{
            throw new TurnoNotFoundException(messageSource.getMessage("turno.error.time.not.exists",null,Locale.getDefault()));
        }

        turnoPersona.setId_horario(dto.getId_horario());
        turnoPersona.setUser(user);
        turnoPersona.setFecha(formatDate.replaceWithFormat(dto.getFecha()));


        sendgrid.emailTurno(turnoPersona.getUser().getEmail(), turnoPersona.getUser().getFirstname(), turnoPersona.getUser().getLastname(), turnoPersona.getFecha(), getHorarioForEmail(turnoPersona.getId_horario()));

        return projectionFactory.createProjection(TurnoSaveResponseDto.class, repository.save(turnoPersona));
    }

    @Override
    public String delete(TurnoPersonaDto dto, HttpServletRequest req) throws TurnoNotFoundException, InvalidUserException {
        //Verifica existencia del turno en base al parametro fecha y horario recibido, caso contrario, salta excepcion.
        if(!existTurno(dto))
            throw new TurnoNotFoundException(messageSource.getMessage("turno.error.not.exists",null,Locale.getDefault()));

        //Obtenemos el modelo del turno
        TurnoPersona turnoPersona = getTurno(dto);

        //Obtenemos rol del usuario, en base a la autenticacion.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //Obtenemos usuario del token, para verificar si es propietario del turno.
        User user = getUserByToken(req);

        //Si es propietario del turno o si posee rol admin, elimina el turno. Caso contrario manda excepcion.
        if(turnoPersona.getUser().getDni() == user.getDni() ||
            authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(ERole.ROLE_ADMIN.toString()))){

            turnoPersona.setUser(null);
            repository.delete(turnoPersona);
        }else
            throw new InvalidUserException(messageSource.getMessage("user.error.not.authorization", null, Locale.getDefault()));

        return messageSource.getMessage("turno.delete.successful",null,Locale.getDefault());
    }

    @Override
    public TurnoSaveResponseDto update(TurnoPersonaDto dto, HttpServletRequest req) throws TurnoAlreadyExists, TurnoNotFoundException {
        /*if(!existTurno(dto))
            throw new TurnoNotFoundException(messageSource.getMessage("turno.error.not.exists", null, Locale.getDefault()));

        User user = getUserByToken(req);
        TurnoPersona turnoPersona = getTurno(dto);

        if(turnoPersona.getUser().getDni() == user.getDni()){
            turnoPersona.setUser(null);
            repository.delete(turnoPersona);
        }*/

        return projectionFactory.createProjection(TurnoSaveResponseDto.class, dto);

    }

    @Override
    public List<Turno> getAllId() {
        List<Turno> turno = turnoRepository.findAll();
        return turno;
    }

    @Override
    public String getInfo(HttpServletRequest req, Jwt jwt){
        System.out.println(jwt.getToken());
        User user = getUserByToken(req);
        if(user != null){
            return user.getFirstname();
        }
        return "";
    }

    @Override
    public Integer getCantId() {
        Integer cant = Math.toIntExact(turnoRepository.count());
        return cant;
    }

    private User getUserByToken(HttpServletRequest req){
        String token = jwtFilter.getToken(req);
        String username = jwtProvider.getNombreUsuarioFromToken(token);
        return userRepository.findByEmail(username).get();
    }





    private Boolean existTurno(TurnoPersonaDto dto){
            if(repository.getByFechaAndId(formatDate.replaceWithFormat(dto.getFecha()), dto.getId_horario()).isPresent())
                return true;
            return false;
    }

    private TurnoPersona getTurno(TurnoPersonaDto dto){
        TurnoPersona turnoPersona = repository.getByFechaAndId(formatDate.stringToDate(dto.getFecha()), dto.getId_horario()).get();
        turnoPersona.setTurno(null);
        return turnoPersona;
    }

    private LocalTime getHorarioForEmail(Long id){
        Turno turno = turnoRepository.findById(id).get();
        return turno.getHs();
    }

    private Boolean compareTurno(String fecha){
        LocalDate today = LocalDate.now();
        today = formatDate.dateToDate(today);
        if(today.equals(formatDate.replaceWithFormat(fecha)))
            return true;
        return false;
    }

}
