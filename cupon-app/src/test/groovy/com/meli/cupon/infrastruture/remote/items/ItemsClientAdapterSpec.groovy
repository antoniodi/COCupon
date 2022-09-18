package com.meli.cupon.infrastruture.remote.items

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import com.meli.cupon.application.spi.ItemsClient
import com.meli.cupon.domain.model.entities.IdItem
import com.meli.cupon.domain.model.entities.Item
import com.meli.cupon.infrastruture.config.FinagleConfig
import com.meli.cupon.testutils.DtabAdapter
import com.meli.cupon.testutils.WireMockInitializer
import com.twitter.finagle.Service
import com.twitter.finagle.http.Request
import com.twitter.finagle.http.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import reactor.test.StepVerifier
import spock.lang.Specification
import spock.lang.Subject

import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.ok

@SpringBootTest(classes = [FinagleConfig.class])
@EnableAutoConfiguration
@ContextConfiguration(initializers = [WireMockInitializer])
//@AutoConfigureWireMock
class ItemsClientAdapterSpec extends Specification {

    @Value('${test.wiremock.https-port}')
    String wiremockPort

    @Autowired
    WireMockServer wireMockServer

    @Autowired
    Service<Request, Response> httpClient

    ObjectMapper mapper = new ObjectMapper()

    @Subject
    ItemsClient itemsClient

    def setup() {
        DtabAdapter.setBase("/endpoint => /\$/inet/localhost/$wiremockPort")
        itemsClient = new ItemsClientAdapter(httpClient, mapper)
        wireMockServer.resetAll()
    }


    def "ObtenerItem"() {
        print("Hola bebe${ wireMockServer.baseUrl() }--------->")
        given:
        def idItem = "MLA1"
        wireMockServer.stubFor(get("/items/${idItem}")
                .willReturn(ok()
                        .withBodyFile("getItem/getItem_200.json")))

        and: "item esperado"
        def itemEsperado = new Item(new IdItem("MLA1"), BigDecimal.TEN, true)

        when:
        def mono = itemsClient.obtenerItem(new IdItem("MLA1"))

        then:
        StepVerifier.create(mono)
                .expectNextMatches(item -> item == itemEsperado)
                .expectComplete()
                .verify()
    }
}
