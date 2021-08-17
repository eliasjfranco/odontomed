package com.odontomed.controller;

import com.odontomed.service.Impl.TurnoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/turno")
public class TurnoController {

    @Autowired
    TurnoServiceImpl service;
    @Autowired
    MessageSource messageSource;

    @GetMapping
    public ResponseEntity<?> obtenerTurnos(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageSource.getMessage("turnos.error.not.found",null, Locale.getDefault()));
        }
    }
}
