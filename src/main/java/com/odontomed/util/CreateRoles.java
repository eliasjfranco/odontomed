package com.odontomed.util;

import com.odontomed.model.ERole;
import com.odontomed.model.Role;
import com.odontomed.service.Impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CreateRoles { //implements CommandLineRunner {

    /*@Autowired
    RoleServiceImpl service;

    @Override
    public void run(String... args) throws Exception {
        Role roleAdmin = new Role(ERole.ROLE_ADMIN);
        Role roleUser = new Role(ERole.ROLE_USER);
        service.save(roleAdmin);
        service.save(roleUser);
    }*/
}
