package com.meli.cupon.application.api;

import com.meli.cupon.application.spi.ItemsClient;
import com.meli.cupon.domain.excepcion.MontoDelCuponInsuficienteException;
import com.meli.cupon.domain.model.entities.Cupon;
import com.meli.cupon.domain.model.entities.IdItem;
import com.meli.cupon.domain.model.entities.ListaDeCompraSugerida;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class CuponServiceImpl implements CuponService {

    private static final Logger LOG = LoggerFactory.getLogger(CuponServiceImpl.class);

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
            }).handle(((listaDeCompraSugerida, sink) -> {
                if (listaDeCompraSugerida.itemsSugeridos().isEmpty()) {
                    LOG.info("El monto del cupon es insuficiente para comprar al menos un item favorito [cupon={}, items={}]",
                        cupon.valor(), itemsFavoritos);
                    sink.error(new MontoDelCuponInsuficienteException());
                } else {
                 sink.next(listaDeCompraSugerida);
                }
            }));
    }
}
