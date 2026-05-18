package com.metodologia.bodyPaint.feature.controllers.delete;

import com.metodologia.bodyPaint.feature.dtos.response.ProductoResponse;
import com.metodologia.bodyPaint.feature.services.interfaces.domain.IDarDeBajaProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Para que no te rebote el navegador
public class DarDeBajaProductoController {

    private final IDarDeBajaProductoService service;

    @DeleteMapping("/{id}/baja")
    public ResponseEntity<ProductoResponse> darDeBaja(@PathVariable Long id) {
        return ResponseEntity.ok(service.ejecutar(id));
    }
}