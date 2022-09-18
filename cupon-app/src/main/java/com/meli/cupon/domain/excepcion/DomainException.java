package com.meli.cupon.domain.excepcion;

public abstract class DomainException extends RuntimeException {

    public DomainException(String mensaje) {
        super(mensaje);
    }
}
