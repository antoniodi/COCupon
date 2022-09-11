package com.meli.cupon.domain.model.entities;

import org.springframework.util.Assert;

import java.util.Objects;

public class IdItem {

    private final String valor;

    public IdItem(String valor) {
        Assert.hasText(valor, "El valor del identificador del item se encuentra vacio.");
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdItem idItem = (IdItem) o;
        return Objects.equals(valor, idItem.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return "IdItem{" +
            "valor='" + valor + '\'' +
            '}';
    }
}
