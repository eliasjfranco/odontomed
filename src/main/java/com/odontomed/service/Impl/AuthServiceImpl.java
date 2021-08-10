package com.odontomed.service.Impl;

import com.odontomed.model.User;
import com.odontomed.repository.UserRepository;
import com.odontomed.service.Interface.IAuth;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthServiceImpl implements IAuth {

    @Autowired
    UserRepository repository;

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
