package com.metodologia.bodyPaint.feature.controllers.post;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metodologia.bodyPaint.feature.dtos.request.ClienteRequest;
import com.metodologia.bodyPaint.feature.services.interfaces.domain.IClienteCreateService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClientePostController {

    private final IClienteCreateService clienteCreateService;

    @PostMapping
    public ResponseEntity<Void> registrar(@Valid @RequestBody ClienteRequest request) {
        clienteCreateService.Crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
