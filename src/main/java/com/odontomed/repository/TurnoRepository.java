package com.odontomed.repository;

import com.odontomed.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TurnoRepository extends JpaRepository <Turno, Long> {

    Optional<Turno> findById(Long id);
}
