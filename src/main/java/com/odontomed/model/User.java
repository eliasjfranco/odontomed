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
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Table(name = "usuario")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
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

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fecha;

    @Column(name = "dni", length = 15, nullable = false)
    private String dni;

    @Column(name = "password")
    private String password;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @ElementCollection(targetClass = GrantedAuthority.class)
    private Collection<? extends GrantedAuthority> authorities;

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

    public static UserDetails build(User user){
        List<GrantedAuthority> authorities = user.getRole()
                .stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    @Builder
    public User(String firstname, String lastname, String tel, String email, LocalDate fecha, String dni, String password, Collection<? extends GrantedAuthority> authorities, Set<Role> role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.tel = tel;
        this.email = email;
        this.fecha = fecha;
        this.dni = dni;
        this.password = password;
        this.authorities = authorities;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
