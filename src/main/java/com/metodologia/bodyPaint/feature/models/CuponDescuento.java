package com.metodologia.bodyPaint.feature.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuponDescuento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    private LocalDate fechaDesde;

    private LocalDate fechaHasta;

    private Double valorDescuento;

    @Enumerated(EnumType.STRING)
    private TipoDescuento tipoDescuento;

    @ManyToMany
    private List<Cliente> clientes;

    private boolean usado;
}
