package com.meli.cupon.domain.model.entities;

import java.math.BigDecimal;

public record Cupon(BigDecimal valor) {

    public Cupon {
        if (valor.signum() <= 0) throw new IllegalArgumentException("El valor del cupon debe ser mayor a cero.");
    }
}