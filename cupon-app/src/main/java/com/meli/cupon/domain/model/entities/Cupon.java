package com.meli.cupon.domain.model.entities;

import java.math.BigDecimal;
import java.util.Objects;

public class Cupon {

    public final BigDecimal valor;

    public Cupon(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cupon cupon = (Cupon) o;
        return Objects.equals(valor, cupon.valor);
    }

    @Override
    public String toString() {
        return "Cupon{" +
            "valor=" + valor +
            '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
