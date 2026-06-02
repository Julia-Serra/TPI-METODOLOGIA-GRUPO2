package com.metodologia.bodyPaint.feature.services.impl.domain;

import com.metodologia.bodyPaint.config.exceptions.BadRequestException;
import com.metodologia.bodyPaint.feature.dtos.request.AplicarCuponRequest;
import com.metodologia.bodyPaint.feature.dtos.response.AplicarCuponResponse;
import com.metodologia.bodyPaint.feature.models.Cliente;
import com.metodologia.bodyPaint.feature.models.CuponDescuento;
import com.metodologia.bodyPaint.feature.models.TipoDescuento;
import com.metodologia.bodyPaint.feature.repositories.ClienteRepository;
import com.metodologia.bodyPaint.feature.repositories.CuponDescuentoRepository;
import com.metodologia.bodyPaint.feature.services.interfaces.domain.IAplicarCuponService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AplicarCuponService implements IAplicarCuponService {

    private final ClienteRepository clienteRepository;
    private final CuponDescuentoRepository cuponRepository;

    @Override
    public AplicarCuponResponse aplicar(AplicarCuponRequest request) {

        Cliente cliente = clienteRepository
                .findByEmailIgnoreCase(request.getEmailCliente())
                .orElseThrow(() ->
                        new BadRequestException("Cliente no encontrado"));

        CuponDescuento cupon = cuponRepository
                .findByCodigo(request.getCodigoCupon())
                .orElseThrow(() ->
                        new BadRequestException("Código inválido"));

        if (!cupon.getClientes().contains(cliente)) {

            throw new BadRequestException(
                    "El cupón no pertenece al cliente");
        }

        if (cupon.isUsado()) {

            throw new BadRequestException(
                    "El cupón ya fue utilizado");
        }

        LocalDate hoy = LocalDate.now();

        if (hoy.isBefore(cupon.getFechaDesde())
                || hoy.isAfter(cupon.getFechaHasta())) {

            throw new BadRequestException(
                    "El cupón se encuentra vencido");
        }

        double totalOriginal = request.getTotal();

        double descuento;

        if (cupon.getTipoDescuento()
                == TipoDescuento.MONTO_FIJO) {

            descuento = cupon.getValorDescuento();

        } else {

            descuento =
                    totalOriginal *
                            cupon.getValorDescuento() / 100;
        }

        if (descuento >= totalOriginal) {

            throw new BadRequestException(
                    "El valor del cupón debe ser menor al total del pedido");
        }

        double totalFinal = totalOriginal - descuento;

        cupon.setUsado(true);
        cuponRepository.save(cupon);

        return AplicarCuponResponse.builder()
                .codigoCupon(cupon.getCodigo())
                .totalOriginal(totalOriginal)
                .descuentoAplicado(descuento)
                .totalFinal(totalFinal)
                .build();
    }
}