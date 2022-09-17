package com.meli.cupon.domain.model.entities

import spock.lang.Specification

class CuponSpec extends Specification {

    def "Instancia de Cupon retorna IllegalArgumentException"() {
        when:
        new Cupon(valor)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == mensajeEsperado

        where:
        valor                  || mensajeEsperado
        BigDecimal.valueOf(0)  || "El valor del cupon debe ser mayor a cero."
        BigDecimal.valueOf(-1) || "El valor del cupon debe ser mayor a cero."

    }

    def "Instancia de Cupon es valida"() {
        given:
        def valorCupon = BigDecimal.valueOf(100)

        when:
        def cupon = new Cupon(valorCupon)

        then:
        cupon.valor() == BigDecimal.valueOf(100)
    }
}
