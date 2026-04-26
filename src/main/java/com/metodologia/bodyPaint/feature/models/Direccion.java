package com.metodologia.bodyPaint.feature.models;

import jakarta.persistence.Entity;
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
public class Direccion {

    @Id @GeneratedValue
    private Long id;

    private String pais;
    private String provincia;
    private String localidad;
    private String calle;
    private String numero;
    private String piso;
    private String departamento;
}
