package com.meli.cupon.domain.excepcion;

public class MontoDelCuponInsuficienteException extends DomainException {

    public MontoDelCuponInsuficienteException() {
        super("El monto del cupon es insuficiente para comprar al menos un item favorito");
    }
}
