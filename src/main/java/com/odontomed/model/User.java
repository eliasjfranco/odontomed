package com.odontomed.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@Getter @Setter
@Table(name = "usuario")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", length = 50, nullable = false)
    private String firstname;

    @Column(name = "apellido", length = 30, nullable = false)
    private String lastname;

    @Column(name = "tel", length = 30, nullable = false)
    private String tel;

    @Email
    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fecha;

    @Column(name = "dni", length = 15, nullable = false)
    private String dni;

    @Column(name = "password")
    private String password;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @OneToMany(mappedBy = "user")
    private Set<TurnoPersona> turnoPersona;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Set<Role> role;

    public User(String firstname, String lastname, String tel, String email, LocalDate fecha, String dni, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.tel = tel;
        this.email = email;
        this.fecha = fecha;
        this.dni = dni;
        this.password = password;
    }
}
