package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("Andy", "Kobe", "James", "William")).log();
    }

    public Mono<String> nameMono() {
        return Mono.just("Andy").log();
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();
        service.namesFlux().subscribe(str -> System.out.printf("Flux name: %s%n", str));
        service.nameMono().subscribe(str -> System.out.printf("Mono name: %s%n", str));

    }
}
