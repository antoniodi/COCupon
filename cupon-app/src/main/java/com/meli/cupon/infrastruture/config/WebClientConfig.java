package com.meli.cupon.infrastruture.config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * This web client config was obtained from:
 * https://medium.com/javarevisited/how-to-connect-to-a-restapi-microservice-with-twitters-finagle-client-c1a76cfb528c
 */
@Configuration
public class WebClientConfig {

    private static final Logger LOG = getLogger(WebClientConfig.class);

    @Bean
    public WebClient webClient(@Value("${server.host:localhost}") String host,
                               @Value("${server.port:8080}") int port,
                               @Value("${global-timeout:5000}") int globalTimeout,
                               @Value("${request-timeout:1000}") int requestTimeout) {

        ClientHttpConnector connector = new ReactorClientHttpConnector(
            HttpClient
                .create()
                .responseTimeout(Duration.ofMillis(globalTimeout))
                .doOnRequest((req, conn) -> {
                    LOG.debug("webClient - {}", req);
                    req.responseTimeout(Duration.ofMillis(requestTimeout));
                })
        );
        return WebClient.builder()
            .baseUrl("http://" + host + ":" + port)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .clientConnector(connector)
            .build();

    }
}