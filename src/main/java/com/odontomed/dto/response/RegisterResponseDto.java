package com.odontomed.dto.response;

import java.time.LocalDate;

public interface RegisterResponseDto {

    Long getId();

    interface Persona{
        String getDni();
        String getFirstname();
        String getLastname();
        LocalDate getFecha();
        String getTel();
        String getEmail();
    }


}
