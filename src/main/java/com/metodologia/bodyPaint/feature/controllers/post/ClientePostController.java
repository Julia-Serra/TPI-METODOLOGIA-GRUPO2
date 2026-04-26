package com.metodologia.bodyPaint.feature.controllers.post;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metodologia.bodyPaint.config.BaseResponse;
import com.metodologia.bodyPaint.feature.dtos.request.ClienteRequest;
import com.metodologia.bodyPaint.feature.mappers.ClienteMapper;
import com.metodologia.bodyPaint.feature.models.Cliente;
import com.metodologia.bodyPaint.feature.services.interfaces.domain.IClienteCreateService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClientePostController {

    private final IClienteCreateService clienteCreateService;

    @PostMapping
    public BaseResponse<Void>crear(@Valid @RequestBody ClienteRequest request) {

        Cliente cliente = ClienteMapper.toEntity(request);

        clienteCreateService.crear(cliente);

        return BaseResponse.ok(null,"Cliente creado");
    }
}
