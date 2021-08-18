package com.odontomed.service.Interface;

import com.odontomed.model.ERole;
import com.odontomed.model.Role;

import java.util.Optional;

public interface IRole {

    Optional<Role> getByRolNombre(ERole role);

    void save(Role role);
}
