package com.reactivespring.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class FluxAndMonoController {

    @GetMapping("/flux")
    public Flux<Integer> getFlex() {
        return Flux.just(1, 2, 3, 4);
    }

    @GetMapping("/mono")
    public Mono<Integer> getMono() {
        return Mono.just(1);
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Long> getStream() {
        return Flux.interval(Duration.ofSeconds(1))
                .takeWhile(l -> l < 10)
                .log();
    }

}
