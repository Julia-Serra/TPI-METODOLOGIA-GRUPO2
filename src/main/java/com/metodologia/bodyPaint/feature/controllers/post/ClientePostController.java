package com.metodologia.bodyPaint.feature.controllers.post;

import org.springframework.web.bind.annotation.*;

import com.metodologia.bodyPaint.config.BaseResponse;
import com.metodologia.bodyPaint.feature.dtos.request.ClienteRequest;
import com.metodologia.bodyPaint.feature.mappers.ClienteMapper;
import com.metodologia.bodyPaint.feature.models.Cliente;
import com.metodologia.bodyPaint.feature.services.interfaces.domain.IClienteCreateService;
import com.metodologia.bodyPaint.feature.dtos.request.DireccionRequest;
import com.metodologia.bodyPaint.feature.models.Direccion;

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
    @PostMapping("/{email}/domicilios")
    public BaseResponse<Void> agregarDireccion(
            @PathVariable String email,
            @Valid @RequestBody DireccionRequest request) {

        Direccion direccion = Direccion.builder()
                .pais(request.getPais())
                .provincia(request.getProvincia())
                .localidad(request.getLocalidad())
                .calle(request.getCalle())
                .numero(request.getNumero())
                .piso(request.getPiso())
                .departamento(request.getDepartamento())
                .build();

        clienteCreateService.agregarDireccion(email, direccion);

        return BaseResponse.ok(null, "Dirección agregada");
    }
}
