package com.metodologia.bodyPaint.feature.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class Producto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String marca;
    private double precio;
    private int stock;

    @Column(name = "stock_minimo")
    private Integer stockMinimo;

    @Column(name = "imagen")
    private String imagen;

    // Se agrega el campo para la User Story de Baja de Producto
    @Builder.Default
    private boolean activo = true;

    /* Solamente si el producto es nuevo se le puede cargar una imagen */
    public boolean esNuevo() {
        return this.imagen == null;
    }
}