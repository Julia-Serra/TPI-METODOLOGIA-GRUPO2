package com.metodologia.bodyPaint.feature.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ConfigurarStockMinimoRequest {

    @NotNull(message = "El stock mínimo no puede ser nulo.")
    @Min(value = 0, message = "El stock mínimo debe ser un número entero y positivo (o cero).")
    private Integer stockMinimo;

    // Getters y Setters
    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }
}