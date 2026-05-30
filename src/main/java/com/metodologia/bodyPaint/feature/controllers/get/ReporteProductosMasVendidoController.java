package com.metodologia.bodyPaint.feature.controllers.get;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.metodologia.bodyPaint.config.BaseResponse;
import com.metodologia.bodyPaint.config.exceptions.BadRequestException;
import com.metodologia.bodyPaint.feature.dtos.response.ReporteProductoMasVendidoResponse;
import com.metodologia.bodyPaint.feature.services.interfaces.domain.IReporteProductosMasVendidosService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteProductosMasVendidoController {

    private final IReporteProductosMasVendidosService reporteService;

    @GetMapping("/productos-mas-vendidos")
    public ResponseEntity<BaseResponse<List<ReporteProductoMasVendidoResponse>>> obtenerReporte(
            @RequestParam(required = false) Integer mes,
            @RequestParam(required = false) Integer anio,
            @RequestParam(required = false) Integer dia) { // Atrapamos el "dia" para validar la falla

        // Criterio de Aceptación: Probar generar reporte filtrado por día (FALLA)
        if (dia != null) {
            throw new BadRequestException("No se permite filtrar el reporte por día. Solo búsquedas mensuales o anuales.");
        }

        // Si pasa la validación, llamamos al servicio
        List<ReporteProductoMasVendidoResponse> reporte = reporteService.generarReporte(mes, anio);
        
        return ResponseEntity.ok(BaseResponse.ok(reporte, "Reporte generado con éxito"));
    }
}