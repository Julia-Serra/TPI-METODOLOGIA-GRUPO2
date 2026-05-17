package com.metodologia.bodyPaint.feature.controllers.put;

import com.metodologia.bodyPaint.feature.dtos.request.ConfigurarStockMinimoRequest;
import com.metodologia.bodyPaint.feature.dtos.response.ProductoResponse;
import com.metodologia.bodyPaint.feature.services.interfaces.domain.IConfigurarStockMinimoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
public class ConfigurarStockMinimoController {

    private final IConfigurarStockMinimoService configurarStockMinimoService;

    public ConfigurarStockMinimoController(IConfigurarStockMinimoService configurarStockMinimoService) {
        this.configurarStockMinimoService = configurarStockMinimoService;
    }

    @PutMapping("/{id}/stock-minimo")
    public ResponseEntity<ProductoResponse> configurarStockMinimo(
            @PathVariable Long id,
            @Valid @RequestBody ConfigurarStockMinimoRequest request) {
        
        ProductoResponse response = configurarStockMinimoService.ejecutar(id, request);
        return ResponseEntity.ok(response);
    }
}