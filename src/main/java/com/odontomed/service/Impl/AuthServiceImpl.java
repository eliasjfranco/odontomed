package com.odontomed.service.Impl;


import com.odontomed.dto.request.LoginRequestDto;
import com.odontomed.dto.request.RegisterRequestDto;
import com.odontomed.dto.response.RegisterResponseDto;
import com.odontomed.exception.EmailAlreadyRegistered;

import com.odontomed.exception.NotRegisteredException;
import com.odontomed.jwt.JwtProvider;
import com.odontomed.model.ERole;
import com.odontomed.model.Role;
import com.odontomed.model.User;
import com.odontomed.repository.RoleRepository;
import com.odontomed.repository.UserRepository;
import com.odontomed.service.Interface.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Service
public class AuthServiceImpl implements IUser {

    @Autowired
    MessageSource messageSource;
    @Autowired
    ProjectionFactory projectionFactory;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository repository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    AuthenticationManager authenticationManager;

    public static final String BEARER = "Bearer ";

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = repository.findByEmail(email).orElseThrow(()
                -> new UsernameNotFoundException(messageSource.getMessage("user.error.email.not.found",null,Locale.getDefault())));
        return User.build(user);
    }

    public RegisterResponseDto createUser(RegisterRequestDto dto) throws IOException, EmailAlreadyRegistered {
        if(repository.findByEmail(dto.getEmail()).isPresent()){
            throw new EmailAlreadyRegistered(messageSource.getMessage("user.error.email.registered", null, Locale.getDefault()));
        }

        User user = User.builder()
                .email(dto.getEmail())
                .dni(dto.getDni())
                .fecha(format(dto.getFecha()))
                .lastname(dto.getLastname())
                .firstname(dto.getFirstname())
                .tel(dto.getTel())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(ERole.ROLE_USER.toString()).get());
        user.setRole(roles);

        User creation = repository.save(user);

        //Crear servicio de envio de email de bienvenida.

        return projectionFactory.createProjection(RegisterResponseDto.class, repository.save(creation));
    }

    @Override
    public String login(LoginRequestDto dto) {
        if(repository.findByEmail(dto.getEmail()).isEmpty()) throw new NotRegisteredException(
                messageSource.getMessage("login.error.email.not.registered", null, Locale.getDefault())
        );

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return BEARER + jwtProvider.generatedToken(authentication);

    }

    public LocalDate format(String string){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate date = LocalDate.parse(string, formatter);
        formatter.format(date);
        return date;
    }
}
