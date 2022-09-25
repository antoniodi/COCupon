package com.meli.cupon.infrastruture.remote.items;

import com.meli.cupon.application.spi.ItemsClient;
import com.meli.cupon.domain.model.entities.IdItem;
import com.meli.cupon.domain.model.entities.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.RoundingMode;

@Component
public class ItemsClientAdapter implements ItemsClient {

    private static final Logger LOG = LoggerFactory.getLogger(ItemsClientAdapter.class);

    //    Intencianalmente dejado como comentario
//    private final Service<Request, Response> httpClient;

//    private final ObjectMapper mapper;

    private final WebClient webClient;

    public ItemsClientAdapter(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<Item> obtenerItem(IdItem idItem) {
        final String MELI_HOST = "https://api.mercadolibre.com";
        return webClient.get()
            .uri(MELI_HOST + "/items/" + idItem.valor())
            .retrieve()
            .onStatus(
                HttpStatus::is4xxClientError,
                error ->
                    error.bodyToMono(String.class)
                        .map(body -> {
                            LOG.warn("El servicio retorno: {} {}",
                                error.statusCode(), body);
                            return new HttpClientErrorException(error.statusCode(), body);
                        }))
            .bodyToMono(ItemDto.class)
            .map(dto -> toItem(idItem, dto))
            .doOnError(t -> LOG.warn("No fue posible obtener el item con identificador: {}, error: {}",
                idItem.valor(), t.getMessage()));
    }

    private Item toItem(IdItem idItem, ItemDto dto) {
        final var precioItem = dto.price.setScale(2, RoundingMode.HALF_UP);
        return new Item(idItem, precioItem, true);
    }

//    Intencianalmente dejado como comentario
//        @Override
//    public Mono<Item> obtenerItem(IdItem idItem) {
//        Request solicitud = Request.apply(Method.Get(), MELI_HOST + "/items/" + idItem.valor());
//        Future<Response> respuesta = httpClient.apply(solicitud);
//
//        return Mono.fromFuture(respuesta.toCompletableFuture())
//            .flatMap(r -> {
//                Response res = (Response) r;
//                LOG.debug("Obtener items - received: {}, body: {}", res, res.contentString());
//                if (res.status() != Status.Ok()) {
//                    final var mensajeDeError = String.format("El servicio retorno %s %s: %s", res.status(),
//                        res, res.getContentString());
//                    Mono.error(new IllegalStateException(mensajeDeError));
//                }
//                try {
//                    final var dto = mapper.readValue(res.contentString(), ItemDto.class);
//                    return Mono.just(dto);
//                } catch (JsonProcessingException e) {
//                    return Mono.error(e);
//                }
//            }).doOnError(t -> LOG.warn("No fue posible obtener el item con identificador: {}, error: {}",
//                idItem.valor(), t.getMessage()))
//            .map(dto -> toItem(idItem, dto));
//    }
}
