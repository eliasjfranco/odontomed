package com.odontomed.repository;

import com.odontomed.model.ERole;
import com.odontomed.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String role);
}
