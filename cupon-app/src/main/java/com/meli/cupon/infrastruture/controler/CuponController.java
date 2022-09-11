package com.meli.cupon.infrastruture.controler;

import com.meli.cupon.application.api.CuponService;
import com.meli.cupon.domain.model.entities.Cupon;
import com.meli.cupon.domain.model.entities.IdItem;
import com.meli.cupon.domain.model.entities.ListaDeCompraSugerida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

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
                final List<IdItem> idItems = itemsFavoritosYCupon.item_ids.stream().map(IdItem::new).toList();
                final Cupon cupon = new Cupon(itemsFavoritosYCupon.amount);
                return cuponService.obtenerListaDeCompraSugerida(idItems, cupon);
            }).map(CuponController::toListaDeCompraSugeridaResponseDto)
            .map(ResponseEntity::ok);
    }

    private static ListaDeCompraSugeridaResponseDto toListaDeCompraSugeridaResponseDto(
        ListaDeCompraSugerida listaDeCompraSugerida) {
        ListaDeCompraSugeridaResponseDto dto = new ListaDeCompraSugeridaResponseDto();
        dto.total = listaDeCompraSugerida.getTotal();
        dto.item_ids = listaDeCompraSugerida.getItemsSugeridos().stream().map(IdItem::getValor).toList();
        return dto;

    }

}
