package com.meli.cupon.infrastruture.config;

import com.meli.cupon.application.api.CuponService;
import com.meli.cupon.application.api.CuponServiceImpl;
import com.meli.cupon.application.spi.ItemsClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    CuponService cuponService(ItemsClient itemsClient, com.meli.cupon.domain.services.CuponService cuponService) {
        return new CuponServiceImpl(itemsClient, cuponService);
    }

    @Bean
    com.meli.cupon.domain.services.CuponService domainCuponService() {
        return new com.meli.cupon.domain.services.CuponService();
    }
}
