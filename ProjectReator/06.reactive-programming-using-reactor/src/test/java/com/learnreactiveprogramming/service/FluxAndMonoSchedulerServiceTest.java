package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class FluxAndMonoSchedulerServiceTest {

    private FluxAndMonoSchedulerService fluxService = new FluxAndMonoSchedulerService();

    @Test
    void namesFlux_block() {
        Flux<String> flux_block = fluxService.namesFlux_block()
                .log();

        StepVerifier.create(flux_block)
                .expectNextCount(8)
                .verifyComplete();
    }

    @Test
    void namesFlux_publishOn() {
        Flux<String> flux_block = fluxService.namesFlux_publishOn()
                .log();

        StepVerifier.create(flux_block)
                .expectNextCount(8)
                .verifyComplete();
    }

    @Test
    void namesFlux_subscribeOn() {
        Flux<String> flux_block = fluxService.namesFlux_subscribeOn()
                .log();

        StepVerifier.create(flux_block)
                .expectNextCount(8)
                .verifyComplete();
    }

}
