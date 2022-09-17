package com.meli.cupon.domain.services;

import java.util.*;

public class CuponService {

    /**
     * Encuentra la lista de items favoritos mas costosa que se puede comprar con un cupon
     * sin superar su valor
     *
     * @param items  Lista de items favoritos de un cliente con su respectivos valor
     * @param amount Valor del cupon
     * @return la lista de items favoritos mas costosa que se puede comprar con un cupon
     */
    public List<String> calculate(Map<String, Float> items, Float amount) {

        final var itemsComprables = items.entrySet().stream()
            .filter(producto -> producto.getValue() <= amount)
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .reduce((itemsFavoritosMasCaros, siguienteItemFavorito) -> {
                float valoritemsFavoritosMasCaros = itemsFavoritosMasCaros.getValue() + siguienteItemFavorito.getValue();
                return valoritemsFavoritosMasCaros <= amount ?
                    Map.entry(itemsFavoritosMasCaros.getKey() + siguienteItemFavorito.getKey() + ",", valoritemsFavoritosMasCaros) :
                    itemsFavoritosMasCaros;
            });
//            .reduce(Pair.with("", 0f), (itemsFavoritosMasCaros, siguienteItemFavorito) -> {
//                float valoritemsFavoritosMasCaros = itemsFavoritosMasCaros.getValue() + siguienteItemFavorito.getValue();
//                return valoritemsFavoritosMasCaros <= amount ? Pair.with("", 0f) : Pair.with("", 0f);
//                    Map.entry(itemsFavoritosMasCaros.getKey() + siguienteItemFavorito.getKey() + ",", valoritemsFavoritosMasCaros) :
//                    itemsFavoritosMasCaros;
//            });

        return itemsComprables.isEmpty() ?
            Collections.emptyList() :
            Arrays.stream(itemsComprables.get().getKey().split(",")).toList();
    }
}
