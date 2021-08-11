package com.odontomed.service.Impl;


import com.odontomed.dto.response.RegisterRespondeDto;
import com.odontomed.exception.EmailAlreadyRegistered;
import com.odontomed.service.Interface.IAuth;


import com.odontomed.model.User;
import com.odontomed.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.projection.ProjectionFactory;

import java.util.EmptyStackException;
import java.util.Locale;

public class AuthServiceImpl implements IAuth {

    @Autowired
    MessageSource messageSource;
    @Autowired
    ProjectionFactory projectionFactory;

    @Autowired
    UserRepository repository;

    public RegisterRespondeDto save(User user){
        if(repository.findByEmail(user.getPersona().getEmail()).isPresent()){
            throw new EmailAlreadyRegistered(messageSource.getMessage("user.error.email.registered", null, Locale.getDefault()));
        }
        return projectionFactory.createProjection(RegisterRespondeDto.class, repository.save(user));
    }
}
