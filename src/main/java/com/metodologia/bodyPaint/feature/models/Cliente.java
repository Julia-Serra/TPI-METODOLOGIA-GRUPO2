package com.metodologia.bodyPaint.feature.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.JoinColumn;

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
    @ElementCollection
    @CollectionTable(
            name ="cliente_direcciones",
            joinColumns = @JoinColumn(name = "cliente_id")
    )
    private List<Direccion> direcciones;
}
