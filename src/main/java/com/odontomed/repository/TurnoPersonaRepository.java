package com.odontomed.repository;

import com.odontomed.model.TurnoPersona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoPersonaRepository extends JpaRepository<TurnoPersona, Long> {
}
