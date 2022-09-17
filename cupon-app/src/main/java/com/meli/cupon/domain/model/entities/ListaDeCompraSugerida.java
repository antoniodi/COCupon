package com.meli.cupon.domain.model.entities;

import java.math.BigDecimal;
import java.util.List;

public record ListaDeCompraSugerida(List<IdItem> itemsSugeridos, BigDecimal total) {}