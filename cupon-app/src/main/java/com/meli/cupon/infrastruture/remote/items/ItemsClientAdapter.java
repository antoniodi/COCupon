package com.meli.cupon.infrastruture.remote.items;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.cupon.application.spi.ItemsClient;
import com.meli.cupon.domain.model.entities.IdItem;
import com.meli.cupon.domain.model.entities.Item;
import com.twitter.finagle.Service;
import com.twitter.finagle.http.Method;
import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.Response;
import com.twitter.finagle.http.Status;
import com.twitter.util.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class ItemsClientAdapter implements ItemsClient {

    private static final Logger LOG = LoggerFactory.getLogger(ItemsClientAdapter.class);

    private final String MELI_HOST = "https://api.mercadolibre.com";

    private final Service<Request, Response> httpClient;

    private final ObjectMapper mapper;

//    private final WebClient webClient;

    public ItemsClientAdapter(Service<Request, Response> httpClient, ObjectMapper mapper) {
        this.httpClient = httpClient;
        this.mapper = mapper;
//        this.webClient = webClient;
    }

    @Override
    public Mono<Item> obtenerItem(IdItem idItem) {
        Request request = Request.apply(Method.Get(), "/items/" + idItem.valor());
        request.host(MELI_HOST);
        Future<Response> response = httpClient.apply(request);

        return Mono.fromFuture(response.toCompletableFuture())
            .flatMap(r -> {
                Response res = (Response) r;
                LOG.debug("getItems - received: {}, body: {}", res, res.contentString());
                if (res.status() != Status.Ok()) {
                    final var mensajeDeError = String.format("El servicio retorno %s %s: %s", res.status(),
                        res, res.getContentString());
                    Mono.error(new IllegalStateException(mensajeDeError));
                }
                try {
                    final var dto = mapper.readValue(res.contentString(), ItemDto.class);
                    return Mono.just(dto);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    return Mono.error(e);
                }
            }).doOnError(t -> LOG.warn("No fue posible obtener el item con identificador: {}", idItem.valor()))
            .map(dto -> toItem(idItem, dto));
    }

    private Item toItem(IdItem idItem, ItemDto dto) {
        final var precioItem = dto.price.setScale(2, RoundingMode.HALF_UP);
        return new Item(idItem, precioItem, true);
    }

//    public Mono<Item> obtenerItem2(IdItem idItem) {
//        return webClient.get()
//            .uri(uriBuilder -> uriBuilder
//                .path("/items/{id}")
//                .host(MELI_HOST)
//                .build(idItem.valor()))
//            .retrieve()
//            .bodyToMono(ItemDto.class)
//            .map(dto -> toItem(idItem, dto));
//    }
}
