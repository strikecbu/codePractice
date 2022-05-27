package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxAndMonoGeneratorService {

    public static void main(String[] args) {
        FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();
        service.namesFlux()
                .subscribe(str -> System.out.printf("Flux name: %s%n", str));
        service.nameMono()
                .subscribe(str -> System.out.printf("Mono name: %s%n", str));

    }

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

    public Flux<String> namesFlux_transform(int length) {
        Function<Flux<String>, Flux<String>> filterFn = (Flux<String> flux) -> flux.map(String::toUpperCase)
                .filter(str -> str.length() > length)
                .flatMap(this::splitString);

        return Flux.fromIterable(List.of("Andy", "Kobe", "James", "Zol"))
                .transform(filterFn)
                .log();
    }

    public Flux<String> namesFlux_emptyDefault(int length) {
        Function<Flux<String>, Flux<String>> filterFn = (Flux<String> flux) -> flux.map(String::toUpperCase)
                .filter(str -> str.length() > length)
                .flatMap(this::splitString);

        return Flux.fromIterable(List.of("Andy", "Kobe", "James", "Zol"))
                .transform(filterFn)
                .defaultIfEmpty("default")
                .log();
    }

    public Flux<String> namesFlux_switchIfEmpty(int length) {
        Function<Flux<String>, Flux<String>> filterFn = (Flux<String> flux) -> flux.map(String::toUpperCase)
                .flatMap(this::splitString);

        Flux<String> defaultFlux = Flux.just("default")
                .transform(filterFn);

        return Flux.fromIterable(List.of("Andy", "Kobe", "James", "Zol"))
                .filter(str -> str.length() > length)
                .transform(filterFn)
                .switchIfEmpty(defaultFlux)
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

    public Mono<String> namesMono_emptyDefault(int length) {

        return Mono.just("alex")
                .transform((Mono<String> mono) -> this.namesMono_map_filter(length))
                .defaultIfEmpty("default")
                .log();
    }

    public Mono<String> namesMono_map_filter_switchIfEmpty(int length) {
        Function<Mono<String>, Mono<String>> filterFn = (Mono<String> mono) -> mono.map(String::toUpperCase);

        Mono<String> aDefault = Mono.just("default")
                .transform(filterFn);

        return Mono.just("alex")
                .filter(str -> str.length() > length)
                .transform(filterFn)
                .switchIfEmpty(aDefault)
                .log();
    }

    public Flux<String> explore_concatwith() {
        Flux<String> abFlux = Flux.just("A", "B");
        Flux<String> cdFlux = Flux.just("C", "D");
        Flux<String> efFlux = Flux.just("E", "F");

        return abFlux.concatWith(cdFlux)
                .concatWith(efFlux)
                .log();
    }

    public Flux<String> explore_mergeWith() {
        Flux<String> abFlux = Flux.just("A", "B")
                .delayElements(Duration.ofMillis(125));
        Flux<String> cdFlux = Flux.just("C", "D")
                .delayElements(Duration.ofMillis(100));

        return abFlux.mergeWith(cdFlux)
                .log();
    }

    public Flux<String> explore_concatwith_mono() {
        Mono<String> aMono = Mono.just("A");
        Mono<String> bMono = Mono.just("C");

        return aMono.concatWith(bMono)
                .log();
    }

    public Flux<String> explore_mergeWith_mono() {
        Flux<String> abMono = Flux.just("A", "B");
        Mono<String> cMono = Mono.just("C");

        return abMono.mergeWith(cMono)
                .log();
    }
}
