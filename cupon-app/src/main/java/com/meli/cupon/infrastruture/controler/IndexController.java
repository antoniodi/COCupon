package com.meli.cupon.infrastruture.controler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class IndexController {

    @GetMapping("/up")
    public Mono<ResponseEntity<String>> up() {
        return Mono.just(ResponseEntity.ok("The cupon-app is up and running"));
    }
}
