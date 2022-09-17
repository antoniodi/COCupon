package com.meli.cupon.infrastruture.remote;

import com.twitter.finagle.Service;
import com.twitter.finagle.SimpleFilter;
import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.Response;
import com.twitter.finagle.http.Status;
import com.twitter.util.Future;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;

public class HttpClientErrorsResponseFilter extends SimpleFilter<Request, Response> {

    @Override
    public Future<Response> apply(Request solicitud, Service<Request, Response> servicio) {
        return servicio
            .apply(solicitud)
            .filter(
                respuesta -> {
                    final Status status = respuesta.status();
                    if (400 <= status.code() && status.code() < 500) {
                        return throwHttpClientError(respuesta, status);
                    }
                    return true;
                });
    }

    private static Void throwHttpClientError(Response respuesta, Status status) {
        final String mensajeDeError = String.format("El servicio retorno %s %s: %s", status.code(),
            status.reason(), respuesta.getContentString());
        throw new HttpClientErrorException(mensajeDeError, HttpStatus.valueOf(status.code()), status.reason(), null,
            respuesta.getContentString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }
}
