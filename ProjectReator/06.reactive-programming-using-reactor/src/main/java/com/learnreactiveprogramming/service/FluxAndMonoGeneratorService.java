package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("Andy", "Kobe", "James", "William"))
                .log();
    }

    public Mono<String> nameMono() {
        return Mono.just("Andy")
                .log();
    }

    public Mono<String> namesMono_map_filter(int length) {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(str -> str.length() > length);
    }

    public Flux<String> namesFlux_flatmap(int length) {
        return Flux.fromIterable(List.of("Andy", "Kobe", "James", "Zol"))
                .map(String::toUpperCase)
                .filter(str -> str.length() > length)
                .flatMap(this::splitString)
                .log();
    }

    private Flux<String> splitString(String str) {
        return Flux.fromArray(str.split(""));
    }

    public Flux<String> namesFlux_flatMap_async(int length) {
        return Flux.fromIterable(List.of("Andy", "Kobe", "James", "Zol"))
                .map(String::toUpperCase)
                .filter(str -> str.length() > length)
                .flatMap(this::splitString_with_delay)
                .log();
    }

    public Mono<List<String>> namesMono_flatMap(int length) {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(str -> str.length() > length)
                .flatMap(this::splitStringToList);
    }
    public Flux<String> namesMono_flatMapMany(int length) {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(str -> str.length() > length)
                .flatMapMany(this::splitString);
    }



    private Mono<List<String>> splitStringToList(String s) {
        List<String> strings = List.of(s.split(""));
        return Mono.just(strings);
    }

    private Flux<String> splitString_with_delay(String str) {
        long randMillis = new Random().nextInt(1000);
        return Flux.fromArray(str.split(""))
                .delayElements(Duration.ofMillis(randMillis));
    }


    public static void main(String[] args) {
        FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();
        service.namesFlux()
                .subscribe(str -> System.out.printf("Flux name: %s%n", str));
        service.nameMono()
                .subscribe(str -> System.out.printf("Mono name: %s%n", str));

    }
}
