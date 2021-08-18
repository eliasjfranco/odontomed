package com.odontomed.service.Impl;

import com.odontomed.model.ERole;
import com.odontomed.model.Role;
import com.odontomed.repository.RoleRepository;
import com.odontomed.service.Interface.IRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements IRole {

    @Autowired
    RoleRepository repository;

    @Override
    public Optional<Role> getByRolNombre(ERole role) {
        return repository.findByRolNombre(role);
    }

    @Override
    public void save(Role role) {
        repository.save(role);
    }
}
