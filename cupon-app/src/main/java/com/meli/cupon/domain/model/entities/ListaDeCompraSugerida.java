package com.meli.cupon.domain.model.entities;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class ListaDeCompraSugerida {

    private final List<IdItem> itemsSugeridos;

    private final BigDecimal total;

    public ListaDeCompraSugerida(List<IdItem> itemsSugeridos, BigDecimal total) {
        this.itemsSugeridos = itemsSugeridos;
        this.total = total;
    }

    public List<IdItem> getItemsSugeridos() {
        return itemsSugeridos;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListaDeCompraSugerida that = (ListaDeCompraSugerida) o;
        return Objects.equals(itemsSugeridos, that.itemsSugeridos) && Objects.equals(total, that.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemsSugeridos, total);
    }

    @Override
    public String toString() {
        return "ListaDeCompraSugerida{" +
            "itemsSugeridos=" + itemsSugeridos +
            ", total=" + total +
            '}';
    }
}
