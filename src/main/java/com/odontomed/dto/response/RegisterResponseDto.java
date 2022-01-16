package com.odontomed.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public interface RegisterResponseDto {

    Long getId();
    String getDni();
    String getFirstname();
    String getLastname();
    LocalDate getFecha();
    String getTel();
    String getEmail();


}
