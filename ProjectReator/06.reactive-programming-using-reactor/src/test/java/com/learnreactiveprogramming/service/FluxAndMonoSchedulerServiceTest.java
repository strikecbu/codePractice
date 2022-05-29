package com.learnreactiveprogramming.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import reactor.test.StepVerifier;

@Slf4j
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

    @Test
    void namesFlux_parallel() {
        ParallelFlux<String> flux_block = fluxService.namesFlux_parallel()
                .log();
        log.info("No of processors: {}", Runtime.getRuntime().availableProcessors());
        StepVerifier.create(flux_block)
                .expectNextCount(4)
                .verifyComplete();
    }
    @Test
    void namesFlux_parallel_with_flatmap() {
        Flux<String> flux_block = fluxService.namesFlux_parallel_with_flatmap()
                .log();
        StepVerifier.create(flux_block)
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    void namesFlux_parallel_with_flatMapSequential() {
        Flux<String> flux_block = fluxService.namesFlux_parallel_with_flatMapSequential()
                .log();
        StepVerifier.create(flux_block)
                .expectNext("ANDY", "KOBE", "JAMES", "ZACK")
                .verifyComplete();
    }
}
