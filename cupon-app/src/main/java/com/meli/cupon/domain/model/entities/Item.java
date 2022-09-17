package com.meli.cupon.domain.model.entities;

import java.math.BigDecimal;

public record Item(IdItem itemId, BigDecimal valor, boolean esFavorito) {

    public Item {
        if (valor.signum() <= 0) throw new IllegalArgumentException("El valor del item debe ser mayor a cero.");
    }
}