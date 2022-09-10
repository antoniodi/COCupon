package com.meli.cupon.domain.services;

import java.util.List;
import java.util.Map;

public class CouponService {

    /**
     * Encuentra la lista de productos favoritos mas costosa que se puede comprar con un cupon
     * sin superar su valor
     *
     * @param items la lista de productos favoritos de un cliente
     * @param amount es el valor del cupon
     * @return la lista de productos favoritos mas costosa que se puede comprar con un cupon
     */
    List<String> calculate(Map<String, Float> items, Float amount) {
        return List.of("");
    }
}
