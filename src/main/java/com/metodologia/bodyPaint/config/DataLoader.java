package com.metodologia.bodyPaint.config;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.metodologia.bodyPaint.feature.models.Cliente;
import com.metodologia.bodyPaint.feature.models.Direccion;
import com.metodologia.bodyPaint.feature.models.Rol;
import com.metodologia.bodyPaint.feature.repositories.ClienteRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (clienteRepository.count() == 0) {

            clienteRepository.save(
                Cliente.builder()
                    .nombre("Admin")
                    .apellido("Principal")
                    .email("admin@bodypaint.com")
                    .password(passwordEncoder.encode("1234"))
                    .rol(Rol.ROLE_ADMIN)
                    .build()
            );

            Direccion dir1 = Direccion.builder()
                    .calle("Av. Corrientes")
                    .numero("1234")
                    .localidad("CABA")
                    .build();

            Direccion dir2 = Direccion.builder()
                    .calle("Calle Falsa")
                    .numero("123")
                    .localidad("La Plata")
                    .build();

            clienteRepository.save(
                Cliente.builder()
                    .nombre("Cliente")
                    .apellido("Demo")
                    .email("cliente@bodypaint.com")
                    .password(passwordEncoder.encode("1234"))
                    .rol(Rol.ROLE_CLIENTE)
                    .direcciones(Arrays.asList(dir1, dir2))
                    .build()
            );
        }
    }
}