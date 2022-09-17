package com.meli.cupon.domain.model.entities

import spock.lang.Specification

class ListaDeCompraSugeridaSpec extends Specification {

    def "Instancia de ListaDeCompraSugerida es valida"() {
        given:
        def items = [new IdItem("MLA1"), new IdItem("MLA2")]

        when:
        def listaDeCompraSugerida = new ListaDeCompraSugerida(items, BigDecimal.valueOf(100))

        then:
        with(listaDeCompraSugerida) {
            itemsSugeridos() == [new IdItem("MLA1"), new IdItem("MLA2")]
            total() == BigDecimal.valueOf(100)
        }
    }
}
