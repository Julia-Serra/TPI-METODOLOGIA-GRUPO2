package com.metodologia.bodyPaint.feature.controllers.get;

import com.metodologia.bodyPaint.config.BaseResponse;
import com.metodologia.bodyPaint.feature.dtos.response.ReporteStockMinimoResponse;
import com.metodologia.bodyPaint.feature.services.interfaces.domain.IReporteStockMinimoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteStockMinimoController {

    private final IReporteStockMinimoService reporteStockMinimoService;

    @GetMapping("/stock-minimo")
    public BaseResponse<List<ReporteStockMinimoResponse>> obtenerReporteStockMinimo(
            @RequestParam(defaultValue = "80") double porcentajeAlerta) {

        List<ReporteStockMinimoResponse> reporte =
            reporteStockMinimoService.obtenerProductosCercaAlMinimo(porcentajeAlerta);

        return BaseResponse.ok(reporte, "Reporte de productos cerca del stock mínimo");
    }
}
