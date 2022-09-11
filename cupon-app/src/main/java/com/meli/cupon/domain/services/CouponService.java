package com.meli.cupon.domain.services;

import java.util.*;

public class CouponService {

    /**
     * Encuentra la lista de productos favoritos mas costosa que se puede comprar con un cupon
     * sin superar su valor
     *
     * @param items Lista de productos favoritos de un cliente con su respectivos valor
     * @param amount Valor del cupon
     * @return la lista de productos favoritos mas costosa que se puede comprar con un cupon
     */
    List<String> calculate(Map<String, Float> items, Float amount) {

        final Map.Entry<String, Float> productosComprables = items.entrySet().stream()
            .filter(producto -> producto.getValue() <= amount)
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .reduce(Map.entry("", 0f), (productosFavoritosMasCaros, siguienteProductoFavorito) -> {
                float valorProductosFavoritosMasCaros = productosFavoritosMasCaros.getValue() + siguienteProductoFavorito.getValue();
                return valorProductosFavoritosMasCaros <= amount ?
                    Map.entry(productosFavoritosMasCaros.getKey() + siguienteProductoFavorito.getKey() + ",", valorProductosFavoritosMasCaros) :
                    productosFavoritosMasCaros;
            });

        return Objects.equals(productosComprables.getKey(), "") ?
            List.of() :
            Arrays.stream(productosComprables.getKey().split(",")).toList();
    }
}
