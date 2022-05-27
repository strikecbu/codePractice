package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoGeneratorServiceTest {

    @Test
    void testFlux() {
        FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();
        Flux<String> stringFlux = service.namesFlux();

        StepVerifier.create(stringFlux)
                .expectNext("Andy", "Kobe", "James", "William")
                .verifyComplete();

        StepVerifier.create(stringFlux)
                .expectNextCount(4)
                .verifyComplete();

        StepVerifier.create(stringFlux)
                .expectNext("Andy")
                .expectNextCount(3)
                .verifyComplete();


    }

    @Test
    void testMono() {
        FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();
        Mono<String> nameMono = service.nameMono();
        StepVerifier.create(nameMono)
                .expectNext("Andy")
                .verifyComplete();
    }
}
