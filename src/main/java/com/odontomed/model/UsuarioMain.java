package com.odontomed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class UsuarioMain implements UserDetails {

    private String firstname;
    private String lastname;
    private String tel;
    private String email;
    private LocalDate fecha;
    private String dni;
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UsuarioMain(String firstname, String lastname, String tel, String email, LocalDate fecha, String dni, String password, Collection<? extends GrantedAuthority> authorities) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.tel = tel;
        this.email = email;
        this.fecha = fecha;
        this.dni = dni;
        this.password = password;
        this.authorities = authorities;
    }

    public static UsuarioMain build(User user){

        List<GrantedAuthority> authorities =
                user.getRole()
                .stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getRolNombre().name()))
                .collect(Collectors.toList());
        return new UsuarioMain(user.getFirstname(), user.getLastname(), user.getTel(), user.getEmail(), user.getFecha(), user.getDni(), user.getPassword(), authorities);
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
