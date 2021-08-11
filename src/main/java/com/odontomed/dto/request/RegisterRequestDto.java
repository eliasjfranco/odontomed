package com.odontomed.dto.request;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class RegisterRequestDto {

    @NotBlank(message = "El campo Nombres no puede estar vacío")
    private String firstname;

    @NotBlank(message = "El campo Apellido no puede estar vacío")
    private String lastname;

    @NotBlank(message = "El campo Fecha de Nacimiento no puede estar vacío")
    private LocalDate fecha;

    @NotBlank(message = "El campo Dni no puede estar vacío")
    private String dni;

    @NotBlank(message = "El campo Telefono no puede estar vacío")
    private String tel;

    @NotBlank(message = "El campo Email no puede estar vacío")
    private String email;



}
