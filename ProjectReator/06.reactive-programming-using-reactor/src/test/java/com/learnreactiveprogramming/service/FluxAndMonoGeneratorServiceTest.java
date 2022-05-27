package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoGeneratorServiceTest {

    @Test
    void nameFlux() {
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
}
