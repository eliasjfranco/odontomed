package com.odontomed.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Table(name = "persona")
public class User {

    @Id
    @Email
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    private Persona persona;

    @Column(name = "password")
    private String password;

    @Column(name = "deleted")
    private Boolean deleted = false;



}
