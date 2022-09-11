package com.meli.cupon.application.api;

import com.meli.cupon.domain.model.entities.Cupon;
import com.meli.cupon.domain.model.entities.IdItem;
import com.meli.cupon.domain.model.entities.ListaDeCompraSugerida;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

public class CuponServiceImpl implements CuponService {

    @Override
    public Mono<ListaDeCompraSugerida> obtenerListaDeCompraSugerida(List<IdItem> itemsFavoritos, Cupon cupon) {
        return Mono.just(new ListaDeCompraSugerida(List.of(new IdItem("a"), new IdItem("b")), new BigDecimal("400")));
    }
}
