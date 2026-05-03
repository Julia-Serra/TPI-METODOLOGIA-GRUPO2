package com.metodologia.bodyPaint.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.metodologia.bodyPaint.feature.models.Cliente;
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

            clienteRepository.save(
                Cliente.builder()
                    .nombre("Cliente")
                    .apellido("Demo")
                    .email("cliente@bodypaint.com")
                    .password(passwordEncoder.encode("1234"))
                    .rol(Rol.ROLE_CLIENTE)
                    .build()
            );
        }
    }
}