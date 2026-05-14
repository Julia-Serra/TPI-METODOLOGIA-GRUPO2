package com.metodologia.bodyPaint.feature.models;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Cliente {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
    private String apellido;
    private String email;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Embedded
    private Direccion direccion;
}
