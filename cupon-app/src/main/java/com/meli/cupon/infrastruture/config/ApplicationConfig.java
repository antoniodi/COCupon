package com.meli.cupon.infrastruture.config;

import com.meli.cupon.application.api.CuponService;
import com.meli.cupon.application.api.CuponServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    CuponService cuponService() {
        return new CuponServiceImpl();
    }
}
