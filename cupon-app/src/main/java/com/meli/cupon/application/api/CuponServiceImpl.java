package com.meli.cupon.application.api;

import com.meli.cupon.application.spi.ItemsClient;
import com.meli.cupon.domain.model.entities.Cupon;
import com.meli.cupon.domain.model.entities.IdItem;
import com.meli.cupon.domain.model.entities.ListaDeCompraSugerida;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CuponServiceImpl implements CuponService {

    private final ItemsClient itemsClient;

    private final com.meli.cupon.domain.services.CuponService cuponService;

    public CuponServiceImpl(ItemsClient itemsClient, com.meli.cupon.domain.services.CuponService cuponService) {
        this.itemsClient = itemsClient;
        this.cuponService = cuponService;
    }

    @Override
    public Mono<ListaDeCompraSugerida> obtenerListaDeCompraSugerida(List<IdItem> itemsFavoritos, Cupon cupon) {
        final var itemsFavoritosNoRepetidos = itemsFavoritos.stream().distinct().toList();
        return Flux.fromIterable(itemsFavoritosNoRepetidos)
            .flatMap(itemsClient::obtenerItem)
            .reduce(new HashMap<String, Float>(), (itemsConPrecio, item) -> {
                itemsConPrecio.putIfAbsent(item.itemId().valor(), item.valor().floatValue());
                return itemsConPrecio;
            }).flatMap(itemsConPrecio -> {
                final var listaDeCompraSugerida = cuponService.calculate(itemsConPrecio, cupon.valor().floatValue());

                final var totalCompraSugerida = listaDeCompraSugerida.stream()
                    .map(item -> BigDecimal.valueOf(itemsConPrecio.getOrDefault(item, 0f)))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

                final var itemsListaDeCompraSugerida = listaDeCompraSugerida.stream()
                    .map(IdItem::new)
                    .toList();
                return Mono.just(new ListaDeCompraSugerida(itemsListaDeCompraSugerida, totalCompraSugerida));
            });
    }
}
