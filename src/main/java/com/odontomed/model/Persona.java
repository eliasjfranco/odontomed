package com.odontomed.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Table(name = "persona")
public class Persona {

    @Id
    @Column(name = "dni", length = 15, nullable = false)
    private String dni;

    @Column(name = "nombre", length = 30, nullable = false)
    private String firstname;

    @Column(name = "apellido", length = 30, nullable = false)
    private String lastname;

    @Column(name = "fecha_nacimiento")
    private LocalDate fecha;

    @Column(name = "telefono", length = 20, nullable = false)
    private String tel;

    @Email
    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @OneToOne(mappedBy = "persona")
    private User user;

    @OneToMany(mappedBy = "persona")
    private List<TurnoPersona> turnoPersona;
}
