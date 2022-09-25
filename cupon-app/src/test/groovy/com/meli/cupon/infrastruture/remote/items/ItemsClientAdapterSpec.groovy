package com.meli.cupon.infrastruture.remote.items

import com.github.tomakehurst.wiremock.WireMockServer
import com.meli.cupon.application.spi.ItemsClient
import com.meli.cupon.domain.model.entities.IdItem
import com.meli.cupon.domain.model.entities.Item
import com.meli.cupon.infrastruture.config.WebClientConfig
import com.meli.cupon.testutils.WireMockInitializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.reactive.function.client.WebClient
import reactor.test.StepVerifier
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Subject

import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.ok

@SpringBootTest(classes = [WebClientConfig.class])
@EnableAutoConfiguration
@ContextConfiguration(initializers = [WireMockInitializer])
//@AutoConfigureWireMock
class ItemsClientAdapterSpec extends Specification {

//    @TestConfiguration
//    static class Config {
//        @Bean
//        WireMockServer webServer() {
//            def wireMockConfiguration = WireMockConfiguration.wireMockConfig()
//                    .dynamicPort()
//            def wireMockServer = new WireMockServer(wireMockConfiguration)
//            // required so we can use `baseUrl()` in the construction of `webClient` below
//            wireMockServer.start()
//            return wireMockServer
//        }
//
////        @Bean
////        WebClient webClient(WireMockServer server) {
////            return
////        }
//
//        @Bean
//        ItemsClient client(WebClient webClient1) {
//            def webClient = WebClient.builder().baseUrl(server.baseUrl()).build()
//            return new ItemsClientAdapter(webClient)
//        }
//    }

    @Autowired
    WireMockServer wireMockServer

    @Autowired
    WebClient webClient

    @Subject
    ItemsClient itemsClient

    def setup() {
        itemsClient = new ItemsClientAdapter(webClient)
        wireMockServer.resetAll()
    }

    @Ignore
    def "test de integracion"() {
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
