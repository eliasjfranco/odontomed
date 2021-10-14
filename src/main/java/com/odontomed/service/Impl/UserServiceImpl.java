package com.odontomed.service.Impl;


import com.odontomed.config.SendgridConfig;
import com.odontomed.dto.request.LoginRequestDto;
import com.odontomed.dto.request.RegisterRequestDto;
import com.odontomed.dto.response.RegisterResponseDto;
import com.odontomed.exception.EmailAlreadyRegistered;

import com.odontomed.exception.NotRegisteredException;
import com.odontomed.jwt.JwtProvider;
import com.odontomed.model.*;
import com.odontomed.repository.UserRepository;
import com.odontomed.service.Interface.IUser;
import com.odontomed.util.FormatDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements IUser, UserDetailsService{

    @Autowired
    MessageSource messageSource;
    @Autowired
    ProjectionFactory projectionFactory;
    @Autowired
    BCryptPasswordEncoder encoder;
    @Autowired
    UserRepository repository;
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider provider;
    @Autowired
    FormatDate formatDate;
    @Autowired
    SendgridConfig sendgrid;

    public static final String BEARER = "Bearer ";

    @Override
    public Optional<User> getByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = getByEmail(s).get();
        if(user == null)
            throw new UsernameNotFoundException(messageSource.getMessage("user.error.not.found",null, Locale.getDefault()));
        return UsuarioMain.build(user);
    }

    @Override
    public RegisterResponseDto saveUser(RegisterRequestDto dto) throws IOException, EmailAlreadyRegistered {
        if(repository.findByEmail(dto.getEmail()).isPresent())
            throw new EmailAlreadyRegistered(messageSource.getMessage("user.error.email.registered",null,Locale.getDefault()));
        User user = new User();
        user = dto.getUserFromDto();
        System.out.println(dto.getFecha());
        user.setPassword(encoder.encode(user.getPassword()));
        user.setFecha(formatDate.stringToDate(dto.getFecha()));
        System.out.println(user.getFecha());

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleService.getByRolNombre(ERole.ROLE_USER).get());
        user.setRole(roleSet);

        sendgrid.emailBienvenida(dto.getEmail(), user.getFirstname(), user.getLastname());

        return projectionFactory.createProjection(RegisterResponseDto.class, repository.save(user));

    }

    @Override
    public Jwt login(LoginRequestDto dto) {
        Jwt jwt = new Jwt();
        if(repository.findByEmail(dto.getEmail()).isEmpty()) throw new NotRegisteredException(
                messageSource.getMessage("login.error.email.not.registered", null, Locale.getDefault())
        );

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        jwt.setToken(provider.generateToken(authentication));
        return jwt;

    }
}
