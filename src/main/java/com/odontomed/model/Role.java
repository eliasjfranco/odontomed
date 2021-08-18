package com.odontomed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "role")
@NoArgsConstructor
@Getter @Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre")
    @Enumerated(EnumType.STRING)
    private ERole rolNombre;

    public Role(ERole rolNombre) {
        this.rolNombre = rolNombre;
    }
}
