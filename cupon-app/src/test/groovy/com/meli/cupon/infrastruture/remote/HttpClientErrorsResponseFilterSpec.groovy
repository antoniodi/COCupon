package com.meli.cupon.infrastruture.remote

import com.twitter.finagle.Service
import com.twitter.finagle.http.Request
import com.twitter.finagle.http.Response
import com.twitter.finagle.http.Status
import com.twitter.util.Future
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException
import spock.lang.Specification
import spock.lang.Subject

import java.util.concurrent.CompletionException

class HttpClientErrorsResponseFilterSpec extends Specification {

    @Subject
    def filtro = new HttpClientErrorsResponseFilter()

    def "Lanzar una excepcion si la respuesta http es 4xx"() {
        given: "Una respuesta con un estado HTTP 4xx"
        def respuesta = Stub(Response) {
            getContentString() >> "{\"message\":\"Client error.\"}"
            status() >> new Status(httpStatus.value())
        }

        and: "Un servicio que retorna una respuesta para una solicitud"
        def solicitud = Stub(Request)
        def servicio = Stub(Service<Request, Response>)
        servicio.apply(solicitud) >> Future.value(respuesta)

        when:
        filtro.apply(solicitud, servicio).toCompletableFuture().join()

        then:
        def e = thrown(CompletionException)
        with(e.cause as HttpClientErrorException) {
            statusCode == HttpStatus.valueOf(httpStatus.value())
            message == "El servicio retorno ${httpStatus.value()} $httpStatus.reasonPhrase: {\"message\":\"Client error.\"}"
        }

        where:
        httpStatus << [400, 401, 403, 404, 409].collect{HttpStatus.valueOf(it)}
    }

    def "Continua cuando el codigo de la respuesta http es diferente de 4xx"() {
        given: "Una respuesta con un estado HTTP diferente de 4xx"
        def respuesta = Stub(Response)
        respuesta.status() >> new Status(statusCode)

        and: "Un servicio que retorna una respuesta para una solicitud"
        def solicitud = Stub(Request)
        def servicio = Stub(Service<Request, Response>)
        servicio.apply(solicitud) >> Future.value(respuesta)

        when:
        def respuestaActual = filtro.apply(solicitud, servicio).toCompletableFuture().join()

        then:
        respuestaActual == respuesta

        where:
        statusCode << [100, 200, 300, 500]
    }
}
