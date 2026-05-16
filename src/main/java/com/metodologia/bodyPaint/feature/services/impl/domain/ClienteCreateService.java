package com.metodologia.bodyPaint.feature.services.impl.domain;

import com.metodologia.bodyPaint.feature.models.Direccion;
import org.springframework.stereotype.Service;

import com.metodologia.bodyPaint.config.exceptions.BadRequestException;
import com.metodologia.bodyPaint.feature.models.Cliente;
import com.metodologia.bodyPaint.feature.models.Rol;
import com.metodologia.bodyPaint.feature.repositories.ClienteRepository;
import com.metodologia.bodyPaint.feature.services.interfaces.domain.IClienteCreateService;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ClienteCreateService implements IClienteCreateService {

    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public void crear(Cliente cliente) {
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new BadRequestException("El email ya está registrado");
        }
        cliente.setRol(Rol.ROLE_CLIENTE);
        clienteRepository.save(cliente);

    }

    public void agregarDireccion(String email, Direccion direccion) {

        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("Cliente no encontrado"));
        if (cliente.getDirecciones() == null) {
            cliente.setDirecciones(new ArrayList<>());
        }
        cliente.getDirecciones().add(direccion);

        clienteRepository.save(cliente);
    }
}
