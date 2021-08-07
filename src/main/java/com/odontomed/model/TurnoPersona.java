package com.odontomed.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Table(name = "Turno_Persona")
@IdClass(value = TurnoPersonaPK.class)
public class TurnoPersona {

    @Id
    @Column(name = "fecha")
    private LocalDate fecha;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @Column(name = "id_horario")
    private Turno turno;

    @ManyToOne(cascade = CascadeType.ALL)
    @Column(name = "dni_persona")
    private Persona persona;




}
