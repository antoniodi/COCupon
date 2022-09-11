package com.meli.cupon.application.api;

import com.meli.cupon.domain.model.entities.Cupon;
import com.meli.cupon.domain.model.entities.IdItem;
import com.meli.cupon.domain.model.entities.ListaDeCompraSugerida;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CuponService {

    /**
     * Encuentra la lista de compra sugerida de mayor valor que se puede comprar con un cupon
     * sin superar su valor
     * @param itemsFavoritos Lista de productos favoritos de un cliente
     * @param cupon Valor del cupon
     * @return la lista de compra sugerida de mayor valor que se puede comprar con un cupon
     * sin superar su valor
     */
    Mono<ListaDeCompraSugerida> obtenerListaDeCompraSugerida(List<IdItem> itemsFavoritos, Cupon cupon);

}
