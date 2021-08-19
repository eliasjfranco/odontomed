package com.odontomed.controller;

import com.odontomed.dto.request.TurnoPersonaDto;
import com.odontomed.exception.InvalidUserException;
import com.odontomed.exception.TurnoAlreadyExists;
import com.odontomed.jwt.JwtProvider;
import com.odontomed.jwt.JwtTokenFilter;
import com.odontomed.service.Impl.TurnoPersonaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@RestController
@RequestMapping("/turno")
public class TurnoController {

    @Autowired
    TurnoPersonaServiceImpl service;
    @Autowired
    MessageSource messageSource;

    @GetMapping
    public ResponseEntity<?> obtenerTurnos(HttpServletRequest req){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageSource.getMessage("turnos.error.not.found",null, Locale.getDefault()));
        }
    }

    @PostMapping()
    public ResponseEntity<?> guardarTurno(@RequestBody TurnoPersonaDto dto, HttpServletRequest req){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.save(dto, req));
        } catch (TurnoAlreadyExists e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteTurno(@RequestBody TurnoPersonaDto dto, HttpServletRequest req){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.delete(dto, req));
        } catch (InvalidUserException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
