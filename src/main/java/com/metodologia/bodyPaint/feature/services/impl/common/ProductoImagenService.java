package com.metodologia.bodyPaint.feature.services.impl.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;

import com.metodologia.bodyPaint.config.exceptions.BadRequestException;
import com.metodologia.bodyPaint.feature.dtos.request.ImportarImagenRequest;
import com.metodologia.bodyPaint.feature.models.Producto;
import com.metodologia.bodyPaint.feature.repositories.ProductoRepository;
import com.metodologia.bodyPaint.feature.services.interfaces.common.IProductoImagenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoImagenService implements IProductoImagenService {

    private final ProductoRepository productoRepository;
    // Define la ruta física apuntando a la carpeta 'uploads'
    private final Path rootFolder = Paths.get("uploads"); 

    @Override
    public void importarImagen(Long productoId, ImportarImagenRequest request) {

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new BadRequestException("Producto no encontrado"));

        if (request.getArchivo() == null || request.getArchivo().isEmpty()) {
            throw new BadRequestException("Debe enviar una imagen");
        }

        try {
            String nombreArchivo = request.getArchivo().getOriginalFilename();
            
            // Crea el directorio de almacenamiento si no existe en la raíz
            if (!Files.exists(rootFolder)) {
                Files.createDirectories(rootFolder);
            }

            // Transfiere los bytes del archivo MultipartFile al disco duro
            Files.copy(request.getArchivo().getInputStream(), 
                       this.rootFolder.resolve(nombreArchivo), 
                       StandardCopyOption.REPLACE_EXISTING);

            // Guarda exclusivamente el nombre plano del archivo en la base de datos
            producto.setImagen(nombreArchivo);
            productoRepository.save(producto);

        } catch (IOException e) {
            throw new BadRequestException("Error al escribir el archivo en el servidor: " + e.getMessage());
        }
    }
}