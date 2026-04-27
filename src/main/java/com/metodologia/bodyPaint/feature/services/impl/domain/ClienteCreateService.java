package com.metodologia.bodyPaint.feature.services.impl.domain;

import org.springframework.stereotype.Service;

import com.metodologia.bodyPaint.config.exceptions.BadRequestException;
import com.metodologia.bodyPaint.feature.models.Cliente;
import com.metodologia.bodyPaint.feature.repositories.ClienteRepository;
import com.metodologia.bodyPaint.feature.services.interfaces.domain.IClienteCreateService;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

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
        clienteRepository.save(cliente);

    }


}
