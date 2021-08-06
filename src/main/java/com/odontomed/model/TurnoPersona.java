package com.odontomed.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor @NoArgsConstructor
@Table(name = "Turno_Persona")
public class TurnoPersona {

    @Id
    @Column(name = "fecha")
    private LocalDate fecha;

    //Crear relacion 1 a n con horario de turno.
    @ManyToOne(cascade = CascadeType.ALL)
    @Column(name = "id_horario")
    private Turno turno;

    //Crear relacion n a 1 persona




}
