package com.meli.cupon.infrastruture.controler;

import com.meli.cupon.application.api.CuponService;
import com.meli.cupon.domain.excepcion.MontoDelCuponInsuficienteException;
import com.meli.cupon.domain.model.entities.Cupon;
import com.meli.cupon.domain.model.entities.IdItem;
import com.meli.cupon.domain.model.entities.ListaDeCompraSugerida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.RoundingMode;

@RestController
public class CuponController {

    private final CuponService cuponService;

    @Autowired
    public CuponController(CuponService cuponService) {
        this.cuponService = cuponService;
    }

    @PostMapping("/coupon")
    public Mono<ResponseEntity<ListaDeCompraSugeridaResponseDto>> obtenerListaDeItemsMasCara(
        @RequestBody ItemsFavoritosYCuponRequestDto itemsFavoritosYCupon) {
        return Mono.defer(() -> {
                final var idItems = itemsFavoritosYCupon.item_ids.stream().map(IdItem::new).toList();
                final var cupon = new Cupon(itemsFavoritosYCupon.amount);
                return cuponService.obtenerListaDeCompraSugerida(idItems, cupon);
            }).map(CuponController::toListaDeCompraSugeridaResponseDto)
            .map(ResponseEntity::ok)
            .onErrorResume(t -> {
                if (t instanceof MontoDelCuponInsuficienteException)
                    return Mono.just(ResponseEntity.noContent().build());
                else return Mono.just(ResponseEntity.internalServerError().build());
            });
    }

    private static ListaDeCompraSugeridaResponseDto toListaDeCompraSugeridaResponseDto(
        ListaDeCompraSugerida listaDeCompraSugerida) {
        final var dto = new ListaDeCompraSugeridaResponseDto();
        dto.total = listaDeCompraSugerida.total().setScale(2, RoundingMode.HALF_UP);
        dto.item_ids = listaDeCompraSugerida.itemsSugeridos().stream().map(IdItem::valor).toList();
        return dto;
    }

}
