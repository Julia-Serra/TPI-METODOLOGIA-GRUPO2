package com.metodologia.bodyPaint.feature.specifications;

import com.metodologia.bodyPaint.feature.models.Producto;
import org.springframework.data.jpa.domain.Specification;

public class ProductoSpecification {

    public static Specification<Producto> nombreContiene(String nombre) {
        return (root, query, cb) ->
                nombre == null || nombre.isBlank()
                        ? null
                        : cb.like(
                        cb.lower(root.get("nombre")),
                        "%" + nombre.toLowerCase() + "%"
                );
    }

    public static Specification<Producto> marcaContiene(String marca) {
        return (root, query, cb) ->
                marca == null || marca.isBlank()
                        ? null
                        : cb.like(
                        cb.lower(root.get("marca")),
                        "%" + marca.toLowerCase() + "%"
                );
    }

    public static Specification<Producto> precioMayorIgual(Double precioMin) {
        return (root, query, cb) ->
                precioMin == null
                        ? null
                        : cb.greaterThanOrEqualTo(root.get("precio"), precioMin);
    }

    public static Specification<Producto> precioMenorIgual(Double precioMax) {
        return (root, query, cb) ->
                precioMax == null
                        ? null
                        : cb.lessThanOrEqualTo(root.get("precio"), precioMax);
    }

    public static Specification<Producto> stockMayorIgual(Integer stockMin) {
        return (root, query, cb) ->
                stockMin == null
                        ? null
                        : cb.greaterThanOrEqualTo(root.get("stock"), stockMin);
    }

    public static Specification<Producto> stockMenorIgual(Integer stockMax) {
        return (root, query, cb) ->
                stockMax == null
                        ? null
                        : cb.lessThanOrEqualTo(root.get("stock"), stockMax);
    }

    public static Specification<Producto> datosCompletos() {
        return (root, query, cb) ->
                cb.and(
                        cb.isNotNull(root.get("nombre")),
                        cb.isNotNull(root.get("marca"))
                );
    }
}