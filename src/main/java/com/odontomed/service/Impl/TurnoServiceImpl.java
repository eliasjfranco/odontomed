package com.odontomed.service.Impl;

import com.odontomed.dto.response.TurnoResponseDto;
import com.odontomed.model.TurnoPersona;
import com.odontomed.repository.TurnoPersonaRepository;
import com.odontomed.repository.TurnoRepository;
import com.odontomed.service.Interface.ITurno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TurnoServiceImpl implements ITurno {

    @Autowired
    TurnoPersonaRepository repository;
    @Autowired
    ProjectionFactory projectionFactory;

    @Override
    public List<TurnoPersona> getAll() {
        LocalDate date = LocalDate.now();
        return repository.findAllByFecha(formatDate(date));
    }

    public LocalDate formatDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MMM/yyyy");
        formatter.format(date);
        return date;
    }
}
