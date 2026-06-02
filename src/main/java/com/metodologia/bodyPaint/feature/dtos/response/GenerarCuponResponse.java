package com.metodologia.bodyPaint.feature.dtos.response;

import com.metodologia.bodyPaint.feature.models.TipoDescuento;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class GenerarCuponResponse {

    private String codigo;

    private LocalDate fechaDesde;

    private LocalDate fechaHasta;

    private Double valorDescuento;

    private TipoDescuento tipoDescuento;
}