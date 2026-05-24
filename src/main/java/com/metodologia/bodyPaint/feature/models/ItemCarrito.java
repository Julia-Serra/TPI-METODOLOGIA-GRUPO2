package com.metodologia.bodyPaint.feature.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemCarrito {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Producto producto;

    @ManyToOne
    @JsonIgnore
    private Pedido pedido;

    private int cantidad;
}
