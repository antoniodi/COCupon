package com.meli.cupon.testutils

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.google.common.collect.ImmutableMap
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.event.ContextClosedEvent

class WireMockInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    void initialize(ConfigurableApplicationContext applicationContext) {
        def wireMockConfiguration = WireMockConfiguration.wireMockConfig()
                .dynamicPort()
                .dynamicHttpsPort()
        def wireMockServer = new WireMockServer(wireMockConfiguration)
        wireMockServer.start()

        applicationContext
                .getBeanFactory()
                .registerSingleton("wireMockServer", wireMockServer)

        applicationContext.addApplicationListener(
                applicationEvent -> {
                    if (applicationEvent instanceof ContextClosedEvent) {
                        wireMockServer.stop();
                    }
                }
        )

        TestPropertyValues.of(
                ImmutableMap.of(
                        "test.wiremock.baseurl-ssl",
                        "https://localhost:" + wireMockServer.httpsPort(),
                        "test.wiremock.baseurl",
                        "http://localhost:" + wireMockServer.port(),
                        "wiremock.server.port",
                        String.valueOf(wireMockServer.httpsPort()),
                        "test.wiremock.https-port",
                        String.valueOf(wireMockServer.httpsPort()),
                        "test.wiremock.http-port",
                        String.valueOf(wireMockServer.port())))
                .applyTo(applicationContext)

    }
}
