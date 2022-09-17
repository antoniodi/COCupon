package com.meli.cupon.domain.model.entities

import spock.lang.Specification

class ItemSpec extends Specification {

    def "Instancia de Item retorna IllegalArgumentException"() {
        when:
        new Item(new IdItem("MLA1"), valor, true)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == mensajeEsperado

        where:
        valor                  || mensajeEsperado
        BigDecimal.valueOf(0)  || "El valor del item debe ser mayor a cero."
        BigDecimal.valueOf(-1) || "El valor del item debe ser mayor a cero."

    }

    def "Instancia de Item es valida"() {
        given:
        def valorItem = BigDecimal.valueOf(100)

        when:
        def item = new Item(new IdItem("MLA1"), valorItem, true)

        then:
        with(item) {
            itemId() == new IdItem("MLA1")
            valor() == BigDecimal.valueOf(100)
            esFavorito()
        }
    }
}
