package com.metodologia.bodyPaint.feature.services.impl.domain;

import org.springframework.stereotype.Service;

import com.metodologia.bodyPaint.feature.dtos.request.ClienteRequest;
import com.metodologia.bodyPaint.feature.models.Cliente;
import com.metodologia.bodyPaint.feature.models.Direccion;
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
    public void Crear(ClienteRequest request) {

        Direccion direccion = new Direccion();
        direccion.setPais(request.getPais());
        direccion.setProvincia(request.getProvincia());
        direccion.setLocalidad(request.getLocalidad());
        direccion.setCalle(request.getCalle());
        direccion.setNumero(request.getNumero());
        direccion.setPiso(request.getPiso());
        direccion.setDepartamento(request.getDepartamento());

        Cliente cliente = new Cliente();
        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setEmail(request.getEmail());
        cliente.setDireccion(direccion);

        clienteRepository.save(cliente);
    }
}
