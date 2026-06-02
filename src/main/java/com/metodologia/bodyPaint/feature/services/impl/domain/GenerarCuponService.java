package com.metodologia.bodyPaint.feature.services.impl.domain;

import com.metodologia.bodyPaint.config.exceptions.BadRequestException;
import com.metodologia.bodyPaint.feature.dtos.request.GenerarCuponRequest;
import com.metodologia.bodyPaint.feature.dtos.response.GenerarCuponResponse;
import com.metodologia.bodyPaint.feature.models.Cliente;
import com.metodologia.bodyPaint.feature.models.CuponDescuento;
import com.metodologia.bodyPaint.feature.repositories.ClienteRepository;
import com.metodologia.bodyPaint.feature.repositories.CuponDescuentoRepository;
import com.metodologia.bodyPaint.feature.services.interfaces.domain.IGenerarCuponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class GenerarCuponService
        implements IGenerarCuponService {

    private final ClienteRepository clienteRepository;
    private final CuponDescuentoRepository cuponRepository;

    @Override
    public GenerarCuponResponse generar(
            GenerarCuponRequest request) {

        if (request.getValorDescuento() == null) {
            throw new BadRequestException(
                    "Debe indicar el descuento");
        }

        if (request.getFechaDesde().isBefore(LocalDate.now())
                || request.getFechaHasta().isBefore(LocalDate.now())) {

            throw new BadRequestException(
                    "No se permiten fechas pasadas");
        }

        List<Cliente> clientes =
                clienteRepository.findAllById(
                        request.getClientesIds());

        if (clientes.isEmpty()) {

            throw new BadRequestException(
                    "Debe seleccionar clientes");
        }

        String codigo = generarCodigo();

        CuponDescuento cupon =
                CuponDescuento.builder()
                        .codigo(codigo)
                        .fechaDesde(request.getFechaDesde())
                        .fechaHasta(request.getFechaHasta())
                        .valorDescuento(request.getValorDescuento())
                        .tipoDescuento(request.getTipoDescuento())
                        .clientes(clientes)
                        .usado(false)
                        .build();

        cuponRepository.save(cupon);

        return GenerarCuponResponse.builder()
                .codigo(codigo)
                .fechaDesde(cupon.getFechaDesde())
                .fechaHasta(cupon.getFechaHasta())
                .valorDescuento(cupon.getValorDescuento())
                .tipoDescuento(cupon.getTipoDescuento())
                .build();
    }

    private String generarCodigo() {

        String codigo;

        do {
            codigo =
                    String.valueOf(
                            ThreadLocalRandom.current()
                                    .nextInt(100000,999999)
                    );
        }
        while(cuponRepository.existsByCodigo(codigo));

        return codigo;
    }
}