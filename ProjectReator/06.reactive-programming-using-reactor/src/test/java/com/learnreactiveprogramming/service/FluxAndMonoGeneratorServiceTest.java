package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

public class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();

    @Test
    void testFlux() {
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
        Mono<String> nameMono = service.nameMono();
        StepVerifier.create(nameMono)
                .expectNext("Andy")
                .verifyComplete();
    }

    @Test
    void namesMono_map_filter() {
        Mono<String> stringMono = service.namesMono_map_filter(6);
        StepVerifier.create(stringMono)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void namesFlux_flatmap() {
        Flux<String> stringFlux = service.namesFlux_flatmap(3);
        StepVerifier.create(stringFlux)
                .expectNext("A", "N", "D", "Y", "K", "O", "B", "E", "J", "A", "M", "E", "S")
                .verifyComplete();
    }

    @Test
    void namesFlux_flatmap_async() {
        Flux<String> stringFlux = service.namesFlux_flatMap_async(3);
        StepVerifier.create(stringFlux)
                .expectNextCount(13)
                .verifyComplete();
    }
    @Test
    void namesMono_flatMap() {
        Mono<List<String>> stringFlux = service.namesMono_flatMap(3);
        StepVerifier.create(stringFlux)
                .expectNext(List.of("A", "L", "E", "X"))
                .verifyComplete();
    }
    @Test
    void namesMono_flatMapMany() {
        Flux<String> stringFlux = service.namesMono_flatMapMany(3);
        StepVerifier.create(stringFlux)
                .expectNext("A", "L", "E", "X")
                .verifyComplete();
    }
}
