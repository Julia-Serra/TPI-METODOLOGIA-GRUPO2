package com.metodologia.bodyPaint.feature.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carrito {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ItemCarrito> items = new ArrayList<>();

    public double getTotal() {

        return items.stream()
                .mapToDouble(i ->
                        i.getProducto().getPrecio() * i.getCantidad()
                )
                .sum();
    }
}
