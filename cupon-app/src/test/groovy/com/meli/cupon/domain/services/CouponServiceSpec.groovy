package com.meli.cupon.domain.services

import spock.lang.Specification

class CouponServiceSpec extends Specification {

    def "primer tests con spock"() {
        given: "productos favoritos con su precio"
        def productosFavoritos = [producto1: precioProducto1,
                                                          producto2: precioProducto2,
                                                          producto3: precioProducto3,
                                                          producto4: precioProducto4,
                                                          producto5: precioProducto5]

        when:
        def productosMasCaros = new CouponService().calculate(productosFavoritos, valorCupon)

        then:
        productosMasCaros == productosEsperados

        where:
        producto1 | precioProducto1 | producto2 | precioProducto2 | producto3 | precioProducto3 | producto4 | precioProducto4 | producto5 | precioProducto5 | valorCupon || productosEsperados
        "MLA1"    | 100             | "MLA2"    | 210             | "MLA3"    | 260             | "MLA4"    | 80              | "MLA5"    | 90              | 100        || [""]
        "MLA1"    | 100             | "MLA2"    | 210             | "MLA3"    | 260             | "MLA4"    | 80              | "MLA5"    | 90              | 100        || [""]
        "MLA1"    | 100             | "MLA2"    | 210             | "MLA3"    | 260             | "MLA4"    | 80              | "MLA5"    | 90              | 100        || ["1"]
        "MLA1"    | 100             | "MLA2"    | 210             | "MLA3"    | 260             | "MLA4"    | 80              | "MLA5"    | 90              | 100        || [""]

    }
}
