package com.odontomed.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalTime;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public interface TurnoResponseDto {

    Long getId();
    LocalTime getHs();


}
