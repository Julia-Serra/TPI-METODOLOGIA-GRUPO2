package com.metodologia.bodyPaint.feature.controllers.post;

import com.metodologia.bodyPaint.feature.dtos.request.AplicarCuponRequest;
import com.metodologia.bodyPaint.feature.dtos.response.AplicarCuponResponse;
import com.metodologia.bodyPaint.feature.repositories.CuponDescuentoRepository;
import com.metodologia.bodyPaint.feature.services.interfaces.domain.IAplicarCuponService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cupones")
@RequiredArgsConstructor
public class CuponController {

    private final IAplicarCuponService aplicarCuponService;
    private final CuponDescuentoRepository cuponRepository;

    @PostMapping("/aplicar")
    public ResponseEntity<AplicarCuponResponse> aplicarCupon(
            @RequestBody AplicarCuponRequest request) {

        return ResponseEntity.ok(
                aplicarCuponService.aplicar(request)
        );
    }

    @GetMapping("/cliente/{email}")
    public ResponseEntity<?> obtenerCupones(
            @PathVariable String email){

        return ResponseEntity.ok(
                cuponRepository.findCuponesValidosPorEmail(email)
        );
    }
}