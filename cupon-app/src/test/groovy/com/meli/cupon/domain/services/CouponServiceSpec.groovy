package com.meli.cupon.domain.services

import spock.lang.Specification

class CouponServiceSpec extends Specification {

    def "cuando el cliente tiene no tiene ningun producto favorito"() {
        given: "ningun items favorito"
        Map<String, Float> itemsFavoritos = Map.of()

        when:
        def itemsMasCaros = new CuponService().calculate(itemsFavoritos, 100.00f)

        then:
        itemsMasCaros == []
    }

    def "cuando el cliente tiene un unico producto favorito"() {
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

//    def "primer tests con spock"() {
//        given: "items favoritos con su precio"
//        def itemsFavoritos = ["MLA11": precioProducto1,
//                                  "MLA12": precioProducto2,
//                                  "MLA13": precioProducto3,
//                                  "MLA14": precioProducto4,
//                                  "MLA15": precioProducto5]
//
//        when:
//        def itemsMasCaros = new CuponService().calculate(itemsFavoritos, valorCupon)
//
//        then:
//        itemsMasCaros == itemsEsperados
//
//        where:
//        precioProducto1 | precioProducto2 | precioProducto3 | precioProducto4 | precioProducto5 | valorCupon || itemsEsperados
//        100f            | 210f            | 260f            | 80f             | 90f             | 500f       || ["MLA1”, “MLA2”, “MLA4”, “MLA5"]
//        100f            | 210f            | 260f            | 80f             | 90f             | 100f       || ["MLA1”, “MLA2”, “MLA4”, “MLA5"]
//        100f            | 210f            | 260f            | 80f             | 90f             | 100f       || ["MLA1”, “MLA2”, “MLA4”, “MLA5"]
//        100f            | 210f            | 260f            | 80f             | 90f             | 100f       || ["MLA1”, “MLA2”, “MLA4”, “MLA5"]
//    }
}
