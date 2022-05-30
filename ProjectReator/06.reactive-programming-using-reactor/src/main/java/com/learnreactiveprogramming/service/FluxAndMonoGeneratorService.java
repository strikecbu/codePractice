package com.learnreactiveprogramming.service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static com.learnreactiveprogramming.util.CommonUtil.delay;

@Slf4j
public class FluxAndMonoGeneratorService {

    public static void main(String[] args) {
        FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();
        service.namesFlux()
                .subscribe(str -> System.out.printf("Flux name: %s%n", str));
        service.nameMono()
                .subscribe(str -> System.out.printf("Mono name: %s%n", str));

    }

    public static List<String> getNames() {
        delay(1000);
        return List.of("Andy", "Anne", "Felix", "Robe", "Dannie");
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

    public Flux<String> explore_zip() {
        Flux<String> abFlux = Flux.just("A", "B");
        Flux<String> cdeFlux = Flux.just("C", "D", "E");

        return Flux.zip(abFlux, cdeFlux)
                .map(t -> t.getT1() + t.getT2())
                .log();
    }

    public Flux<String> explore_zipWith_mono() {
        Flux<String> abFlux = Flux.just("A", "B");
        Mono<String> cFlux = Mono.just("C");

        return abFlux.zipWith(cFlux)
                .map(t -> t.getT1() + t.getT2())
                .log();
    }

    public Flux<String> namesFlux_onErrorReturn(int length) {
        Function<Flux<String>, Flux<String>> filterFn = (Flux<String> flux) -> flux
                .filter(str -> str.length() > length);

        return Flux.fromIterable(List.of("Andy", "Kobe", "James", "Zol"))
                .transform(filterFn)
                .doOnNext(name -> {
                    if ("Kobe".equalsIgnoreCase(name)) {
                        throw new IllegalArgumentException();
                    }
                })
                .onErrorReturn(IllegalArgumentException.class, "Error occurred");
    }

    public Flux<String> namesFlux_onErrorResume(int length) {
        Function<Flux<String>, Flux<String>> filterFn = (Flux<String> flux) -> flux
                .filter(str -> str.length() > length);

        Flux<String> resumeFlux = Flux.fromIterable(List.of("Cindy", "LuLu"));

        return Flux.fromIterable(List.of("Andy", "Kobe", "James", "Zol"))
                .transform(filterFn)
                .doOnNext(name -> {
                    if ("Kobe".equalsIgnoreCase(name)) {
                        throw new IllegalArgumentException("Wrong pick!");
                    }
                })
                .onErrorResume(IllegalArgumentException.class, (e) -> {
                    log.info("Some exception occurred", e);
                    return resumeFlux;
                });
    }

    public Flux<String> namesFlux_onErrorContinue(String... names) {

        return Flux.fromIterable(List.of(names))
                .doOnNext(name -> {
                    if ("Kobe".equalsIgnoreCase(name)) {
                        throw new IllegalArgumentException("Wrong pick!");
                    } else if ("Kate".equalsIgnoreCase(name)) {
                        throw new RuntimeException("What are you thinking ?!");
                    }
                })
                .onErrorContinue(IllegalArgumentException.class, (e, name) -> {
                    log.info("Still give you a chance! {}", name);
                });
    }

    public Flux<Integer> explore_flux_generate() {
        return Flux.generate(() -> 1, (state, sink) -> {
            sink.next(state * 2);
            if (state == 10) {
                sink.complete();
            }
            return state + 1;
        });
    }

    public Flux<String> explore_flux_create() {
        return Flux.create(sink -> {
//            getNames().forEach(sink::next);
//            sink.complete();
            CompletableFuture.supplyAsync(FluxAndMonoGeneratorService::getNames)
                    .thenAccept(names -> {
                        names.forEach(sink::next);
                    })
                    .thenRun(sink::complete);
        });
    }

    public Mono<String> explore_mono_create() {
        return Mono.create(monoSink -> monoSink.success("Cube"));
    }

    public Flux<String> explore_flux_handle() {
        return Flux.fromIterable(List.of("Andy", "Anne", "Felix", "Robe", "Dannie"))
                .handle((name, sink) -> {
                    if (name.startsWith("A")) {
                        sink.next(name.toUpperCase());
                    }
                });
    }


}
