package com.meli.cupon.application.spi;

import com.meli.cupon.domain.model.entities.IdItem;
import com.meli.cupon.domain.model.entities.Item;
import reactor.core.publisher.Mono;

public interface ItemsClient {

    /**
     * Retorna la informacion de un item
     *
     * @param idItem identificador del item
     * @return la informacion de un item
     */
    Mono<Item> obtenerItem(IdItem idItem);
}
