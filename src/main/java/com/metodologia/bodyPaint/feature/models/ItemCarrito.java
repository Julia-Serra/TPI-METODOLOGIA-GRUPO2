package com.metodologia.bodyPaint.feature.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

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
    private Pedido pedido;

    private int cantidad;
}
