package com.odontomed.repository;

import com.odontomed.model.TurnoPersona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TurnoPersonaRepository extends JpaRepository<TurnoPersona, Long> {

    @Query(value = "SELECT * from turno_persona t where t.fecha > :fecha order by fecha, id_horario asc", nativeQuery = true)
    List<TurnoPersona> obtenerAllByFecha(LocalDate fecha);

    @Query(value = "Select * from turno_persona t where t.fecha = :fecha and t.id_horario = :id ", nativeQuery = true)
    Optional<TurnoPersona> getByFechaAndId(LocalDate fecha, Long id);
}
