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

    @OneToOne
    private Cliente cliente;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ItemCarrito> items = new ArrayList<>();
}
