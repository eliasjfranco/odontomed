package com.odontomed.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
<<<<<<< HEAD
=======
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.time.LocalDate;
>>>>>>> 3094fd949cc2467fe38d888808499cfd1e4e5b53

@Entity
@AllArgsConstructor @NoArgsConstructor
@Table(name = "usuario")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    private Persona persona;

    @Column(name = "password")
    private String password;

    @Column(name = "deleted")
    private Boolean deleted = false;



}
