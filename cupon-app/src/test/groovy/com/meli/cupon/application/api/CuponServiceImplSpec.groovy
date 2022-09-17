package com.meli.cupon.application.api

import com.meli.cupon.application.spi.ItemsClient
import com.meli.cupon.domain.model.entities.Cupon
import com.meli.cupon.domain.model.entities.IdItem
import com.meli.cupon.domain.model.entities.Item
import reactor.core.publisher.Mono
import spock.lang.Specification
import spock.lang.Subject


class CuponServiceImplSpec extends Specification {

    ItemsClient itemsClient = Mock(ItemsClient)

    com.meli.cupon.domain.services.CuponService domainCuponService = Mock(com.meli.cupon.domain.services.CuponService)

    @Subject
    CuponServiceImpl cuponService = new CuponServiceImpl(itemsClient, domainCuponService)

    def "Obtener lista de compra sugerida cuando hay items repetidos"() {
        given: "Lista de items favoritos y un cupon"
        def itemsFavoritos = [new IdItem ("MLA1"), new IdItem ("MLA1"), new IdItem ("MLA1")]
        def cupon = new Cupon(BigDecimal.TEN)

        and: "Item"
        def item = new Item(new IdItem ("MLA1"), BigDecimal.ONE, true)

        when:
        def listaDeCompraSugerida = cuponService.obtenerListaDeCompraSugerida(itemsFavoritos, cupon).block()

        then:
        1 * itemsClient.obtenerItem(new IdItem ("MLA1")) >> Mono.just(item)
        1 * domainCuponService.calculate(["MLA1" : 1f], 10f) >> ["MLA1"]

        and:
        with(listaDeCompraSugerida) {
            it.total() == BigDecimal.ONE
            it.itemsSugeridos() == [new IdItem("MLA1")]
        }
    }

    def "Obtener lista de compra sugerida cuando NO hay items repetidos"() {
        given: "Lista de items favoritos y un cupon"
        def itemsFavoritos = [new IdItem ("MLA1"), new IdItem ("MLA2"), new IdItem ("MLA3")]
        def cupon = new Cupon(BigDecimal.TEN)

        and: "Item"
        def item1 = new Item(new IdItem ("MLA1"), BigDecimal.ONE, true)
        def item2 = new Item(new IdItem ("MLA2"), BigDecimal.ONE, true)
        def item3 = new Item(new IdItem ("MLA3"), BigDecimal.ONE, true)

        when:
        def listaDeCompraSugerida = cuponService.obtenerListaDeCompraSugerida(itemsFavoritos, cupon).block()

        then:
        1 * itemsClient.obtenerItem(new IdItem ("MLA1")) >> Mono.just(item1)
        1 * itemsClient.obtenerItem(new IdItem ("MLA2")) >> Mono.just(item2)
        1 * itemsClient.obtenerItem(new IdItem ("MLA3")) >> Mono.just(item3)
        1 * domainCuponService.calculate(["MLA1" : 1f, "MLA2" : 1f, "MLA3" : 1f], 10f) >> ["MLA1", "MLA2", "MLA3"]

        and:
        with(listaDeCompraSugerida) {
            it.total() == BigDecimal.valueOf(3f)
            it.itemsSugeridos() == [new IdItem("MLA1"), new IdItem("MLA2"), new IdItem("MLA3")]
        }
    }
}
