package com.metodologia.bodyPaint.feature;

import com.metodologia.bodyPaint.feature.dtos.request.GenerarCuponRequest;
import com.metodologia.bodyPaint.feature.services.interfaces.domain.IGenerarCuponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cupones")
@RequiredArgsConstructor
public class CuponPostController {

    private final IGenerarCuponService generarCuponService;

    @PostMapping
    public ResponseEntity<?> generar(
            @RequestBody GenerarCuponRequest request) {

        return ResponseEntity.ok(
                generarCuponService.generar(request)
        );
    }
}