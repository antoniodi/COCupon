package com.meli.cupon.domain.services

import spock.lang.Specification

class CouponServiceSpec extends Specification {

    def "cuando el cliente tiene no tiene ningun item favorito"() {
        given: "ningun items favorito"
        Map<String, Float> itemsFavoritos = Map.of()

        when:
        def itemsMasCaros = new CuponService().calculate(itemsFavoritos, 100.00f)

        then:
        itemsMasCaros == []
    }

    def "cuando el cliente tiene un unico item favorito"() {
        given: "items favoritos con su precio"
        def itemsFavoritos = ["MLA1": precioProducto1]

        when:
        def itemsMasCaros = new CuponService().calculate(itemsFavoritos, valorCupon)

        then:
        itemsMasCaros == itemsEsperados

        where:
        precioProducto1 | valorCupon || itemsEsperados
        99.99f          | 100.00f    || ["MLA1"]
        100.00f         | 100.00f    || ["MLA1"]
        100.01f         | 100.00f    || []
    }

    def "cuando el cliente tiene uno o mas items favoritoa"() {
        given: "items favoritos con su precio"
        def itemsFavoritos = ["MLA1": precioProducto1,
                              "MLA2": precioProducto2,
                              "MLA3": precioProducto3,
                              "MLA4": precioProducto4,
                              "MLA5": precioProducto5]

        when:
        def itemsMasCaros = new CuponService().calculate(itemsFavoritos, valorCupon)

        then:
        itemsMasCaros == itemsEsperados

        where:
        precioProducto1 | precioProducto2 | precioProducto3 | precioProducto4 | precioProducto5 | valorCupon || itemsEsperados
        100f            | 210f            | 260f            | 80f             | 90f             | 500f       || ["MLA2", "MLA1", "MLA5", "MLA4"]
        100f            | 110f            | 120f            | 130f            | 140f            | 100f       || ["MLA1"]
        99f             | 210f            | 260f            | 1f              | 90f             | 100f       || ["MLA4", "MLA1"]
        100f            | 210f            | 260f            | 80f             | 90f             | 50f        || []
        100f            | 210f            | 260f            | 80f             | 90f             | 0f         || []
    }
}
