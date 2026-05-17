package com.metodologia.bodyPaint.feature.controllers.get;

import com.metodologia.bodyPaint.config.BaseResponse;
import com.metodologia.bodyPaint.config.exceptions.BadRequestException;
import com.metodologia.bodyPaint.feature.dtos.response.ClienteMeResponse;
import com.metodologia.bodyPaint.feature.models.Cliente;
import com.metodologia.bodyPaint.feature.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteGetController {

        private final ClienteRepository clienteRepository;

        @GetMapping("/me")
        public Map<String, Object> obtenerClienteLogueado(Authentication authentication) {

                String email = authentication.getName();

                Cliente cliente = clienteRepository.findByEmail(email)
                                .orElseThrow();

                String rol = authentication.getAuthorities()
                                .iterator()
                                .next()
                                .getAuthority();

                return Map.of(
                                "cliente", cliente,
                                "rol", rol);
        }

        @GetMapping("/email/{email}")
        public BaseResponse buscarPorEmail(
                        @PathVariable String email) {

                Cliente cliente = clienteRepository
                                .findByEmail(email)
                                .orElseThrow(() -> new BadRequestException(
                                                "Cliente no encontrado"));

                return BaseResponse.ok(cliente, "Cliente encontrado");
        }
}