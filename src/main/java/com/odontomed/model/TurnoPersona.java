package com.odontomed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Table(name = "turno_persona")
@IdClass(value = TurnoPersonaPK.class)
public class TurnoPersona {

    @Id
    @Column(name = "fecha")
    private LocalDate fecha;

    @Id
    @Column(name = "id_horario")
    private Long id_horario;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_horario", updatable = false, nullable = false, insertable = false)
    private Turno turno;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dni_persona", referencedColumnName = "dni", nullable = false)
    private User user;




}
