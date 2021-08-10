package com.odontomed.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Table(name = "turno_persona")
@IdClass(value = TurnoPersonaPK.class)
public class TurnoPersona {

    @Id
    @Column(name = "fecha")
    private LocalDate fecha;

    @Id
    @Column(name = "id_horario")
    private Long id_horario;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_horario", updatable = false, nullable = false, insertable = false)
    private Turno turno;

    @ManyToOne(cascade = CascadeType.ALL)
<<<<<<< HEAD
    @JoinColumn(name = "dni_persona", referencedColumnName = "dni", nullable = false)
=======
    @JoinColumn(name = "dni_persona", nullable = false)
>>>>>>> 3094fd949cc2467fe38d888808499cfd1e4e5b53
    private Persona persona;




}
