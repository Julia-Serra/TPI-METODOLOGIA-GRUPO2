package com.metodologia.bodyPaint.feature.mappers;

import com.metodologia.bodyPaint.feature.dtos.request.ClienteRequest;
import com.metodologia.bodyPaint.feature.models.Cliente;
import com.metodologia.bodyPaint.feature.models.Direccion;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ClienteMapper {

    private final PasswordEncoder passwordEncoder;

    public ClienteMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Cliente toEntity(ClienteRequest request) {

        Direccion direccion = Direccion.builder()
                .pais(request.getPais())
                .provincia(request.getProvincia())
                .localidad(request.getLocalidad())
                .calle(request.getCalle())
                .numero(request.getNumero())
                .piso(request.getPiso())
                .departamento(request.getDepartamento())
                .build();

        return Cliente.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getEmail())
                .direcciones(List.of(direccion))
                .build();
    }
}
