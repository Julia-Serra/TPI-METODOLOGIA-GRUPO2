package com.metodologia.bodyPaint.feature.controllers.get;

import com.metodologia.bodyPaint.config.BaseResponse;
import com.metodologia.bodyPaint.config.exceptions.BadRequestException;
import com.metodologia.bodyPaint.feature.models.Cliente;
import com.metodologia.bodyPaint.feature.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteGetController {

    private final ClienteRepository clienteRepository;

    @GetMapping("/me")
    public Cliente obtenerClienteLogueado(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return clienteRepository.findByEmail(email)
                .orElseThrow();
    }
    @GetMapping("/email/{email}")
    public BaseResponse<Cliente> buscarPorEmail(@PathVariable String email) {

        Cliente cliente = clienteRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new BadRequestException("Cliente no encontrado"));

        return BaseResponse.ok(cliente, "Cliente encontrado");
    }
}