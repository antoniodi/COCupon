package com.meli.cupon.domain.model.entities;

import org.springframework.util.Assert;

import java.util.Objects;

public class ItemFavorito {

    private final String itemId;

    private final Float valor;

    public ItemFavorito(String itemId, Float valor) {
//        Assert.hasText(valor, "El valor del item es vacio.");
        this.itemId = itemId;
        this.valor = valor;
    }

    public String getItemId() {
        return itemId;
    }

    public Float getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemFavorito that = (ItemFavorito) o;
        return Objects.equals(itemId, that.itemId) && Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, valor);
    }

    @Override
    public String toString() {
        return "ItemFavorito{" +
            "itemId='" + itemId + '\'' +
            ", valor=" + valor +
            '}';
    }
}
