package com.metodologia.bodyPaint.feature.services.interfaces.domain;

import com.metodologia.bodyPaint.feature.dtos.response.ReporteStockMinimoResponse;
import java.util.List;

public interface IReporteStockMinimoService {
    List<ReporteStockMinimoResponse> obtenerProductosCercaAlMinimo(double porcentajeAlerta);
}
