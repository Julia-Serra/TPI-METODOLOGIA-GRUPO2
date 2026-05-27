package com.metodologia.bodyPaint.feature.services.interfaces.domain;

import java.util.List;
import com.metodologia.bodyPaint.feature.dtos.response.ReporteProductoMasVendidoResponse;

public interface IReporteProductosMasVendidosService {
    List<ReporteProductoMasVendidoResponse> generarReporte(Integer mes, Integer anio);
}