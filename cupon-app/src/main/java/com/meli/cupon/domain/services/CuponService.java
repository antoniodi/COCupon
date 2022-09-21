package com.meli.cupon.domain.services;

import org.javatuples.Pair;

import java.util.*;
import java.util.stream.Stream;

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
            .filter(item -> item.getValue() <= amount)
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .map(entry -> Pair.with(entry.getKey(), entry.getValue()))
            .toList();

        return obtenerLosItemsSugeridos(itemsComprables, amount);
    }

    private List<String> obtenerLosItemsSugeridos(List<Pair<String, Float>> items, Float cupon) {
        final List<Pair<List<String>, Float>> itemsSugeridos = new ArrayList<>(Collections.emptyList());
        int numeroDeItems = items.size();
        for (int i = 0; i < numeroDeItems; i++) {

            List<Pair<String, Float>> subgrupoDeItems = Stream.concat(items.subList(i, numeroDeItems).stream(), items.subList(0, i).stream()).toList();
            Pair<List<String>, Float> itemsMasCaros = obtenerLosItemsMasCaros(subgrupoDeItems, cupon);
            itemsSugeridos.add(itemsMasCaros);
        }
        return itemsSugeridos.stream()
            .max(Comparator.comparing(Pair::getValue1))
            .map(Pair::getValue0)
            .orElse(Collections.emptyList());
    }

    private Pair<List<String>, Float> obtenerLosItemsMasCaros(List<Pair<String, Float>> items, Float cupon) {
        var valorItems = 0f;
        final List<String> listaDeCompraSugerida = new ArrayList<>(Collections.emptyList());
        for (Pair<String, Float> item : items) {

            final var acumuladoItemsMasCaros = valorItems + item.getValue1();
            if (acumuladoItemsMasCaros <= cupon) {
                listaDeCompraSugerida.add(item.getValue0());
                valorItems = acumuladoItemsMasCaros;
            } else break;
        }
        return Pair.with(listaDeCompraSugerida, valorItems);
    }
}
