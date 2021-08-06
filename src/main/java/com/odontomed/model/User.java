package com.odontomed.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import java.time.LocalDate;

@AllArgsConstructor @NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @Email
    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "apellido", length = 30)
    private String lastname;

    @Column(name = "nombre", length = 30)
    private String firstname;

    @Column(name = "tel", length = 20)
    private String tel;

    @Column(name = "dni", length = 15)
    private String dni;

    @Column(name = "fecha_nacimiento")
    private LocalDate fnac;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @Column(name = "password")
    private String password;

}
