package com.odontomed.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor @NoArgsConstructor
public class TurnoPersonaPK {

    private LocalDate fecha;

    private Long id_horario;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TurnoPersonaPK)) return false;
        TurnoPersonaPK that = (TurnoPersonaPK) o;
        return Objects.equals(fecha, that.fecha) && Objects.equals(id_horario, that.id_horario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fecha, id_horario);
    }
}
