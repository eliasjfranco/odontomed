package com.odontomed.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.odontomed.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RegisterRequestDto {

    @NotBlank(message = "El campo Nombres no puede estar vacío")
    private String firstname;

    @NotBlank(message = "El campo Apellido no puede estar vacío")
    private String lastname;

    @NotNull(message = "El campo Fecha de Nacimiento no puede estar vacío")
    private String fecha;

    @NotBlank(message = "El campo Dni no puede estar vacío")
    private String dni;

    @NotBlank(message = "El campo Telefono no puede estar vacío")
    private String tel;

    @NotBlank(message = "El campo Email no puede estar vacío")
    private String email;

    @NotBlank(message = "El campo Password no puede estar vacío")
    private String password;

    @JsonIgnore
    public User getUserFromDto() {
        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setDni(dni);
        user.setTel(tel);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

}
