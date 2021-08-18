package com.odontomed.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.odontomed.model.ERole;
import com.odontomed.model.Role;

import java.time.LocalDate;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public interface RegisterResponseDto {

    Long getId();
    String getDni();
    String getFirstname();
    String getLastname();
    LocalDate getFecha();
    String getTel();
    String getEmail();
    Set<com.odontomed.model.Role> getRole();

    interface Role{
        Long getId();
        ERole getRolNombre();
    }


}
