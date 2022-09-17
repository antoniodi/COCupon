package com.meli.cupon.infrastruture.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.cupon.application.spi.ItemsClient;
import com.meli.cupon.infrastruture.remote.HttpClientErrorsResponseFilter;
import com.meli.cupon.infrastruture.remote.items.ItemDto;
import com.meli.cupon.infrastruture.remote.items.ItemsClientAdapter;
import com.twitter.finagle.Service;
import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.Response;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfrastructureRemoteConfig {

//    @Bean
//    ItemsClient itemsClient(
//        @Qualifier("routingResilientHttpClient") Service<Request, Response> routingResilientHttpClient,
//        ObjectMapper objectMapper) {
//        HttpClientErrorsResponseFilter httpClientErrorsResponseFilter =
//            new HttpClientErrorsResponseFilter();
//        Service<Request, Response> webService = httpClientErrorsResponseFilter.andThen(routingResilientHttpClient)
//        return new ItemsClientAdapter(webService, objectMapper);
//    }

//    @Bean
//    ItemsClient itemsClient(
//        @Qualifier("routingResilientHttpClient") Service<Request, Response> routingResilientHttpClient,
//        ObjectMapper objectMapper) {
//        HttpClientErrorsResponseFilter httpClientErrorsResponseFilter =
//            new HttpClientErrorsResponseFilter();
//        JsonDes
//        Service<Request, ItemDto> servicioWeb = httpClientErrorsResponseFilter
//            .andThen(routingResilientHttpClient);
//        return new ItemsClientAdapter()
//    }
}
