package com.metodologia.bodyPaint.feature.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id @GeneratedValue
    private Long id;

    private String nombre;
    private String apellido;
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private Direccion direccion;
}

