package com.odontomed.service.Impl;


import com.odontomed.dto.request.RegisterRequestDto;
import com.odontomed.dto.response.RegisterResponseDto;
import com.odontomed.exception.EmailAlreadyRegistered;

import com.odontomed.model.Role;
import com.odontomed.model.User;
import com.odontomed.repository.RoleRepository;
import com.odontomed.repository.UserRepository;
import com.odontomed.service.Interface.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    @Override
    public UserDetails loadUserByUsername(String email) {
        return null;
    }

    public RegisterResponseDto createUser(RegisterRequestDto dto) throws IOException, EmailAlreadyRegistered {
        if(repository.findByEmail(dto.getEmail()).isPresent()){
            throw new EmailAlreadyRegistered(messageSource.getMessage("user.error.email.registered", null, Locale.getDefault()));
        }

        User user = User.builder()
                .email(dto.getEmail())
                .dni(dto.getDni())
                .fecha(dto.getFecha())
                .lastname(dto.getLastname())
                .firstname(dto.getFirstname())
                .tel(dto.getTel())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("ROLE_USER"));
        user.setRole(roles);

        //Crear servicio de envio de email de bienvenida.

        return projectionFactory.createProjection(RegisterResponseDto.class, repository.save(user));
    }
}
