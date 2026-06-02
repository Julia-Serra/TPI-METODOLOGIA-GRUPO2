package com.metodologia.bodyPaint.feature.dtos.request;

import com.metodologia.bodyPaint.feature.models.TipoDescuento;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class GenerarCuponRequest {

    private List<Long> clientesIds;

    private LocalDate fechaDesde;

    private LocalDate fechaHasta;

    private Double valorDescuento;

    private TipoDescuento tipoDescuento;
}