package com.learnreactiveprogramming.service;

import com.learnreactiveprogramming.util.CommonUtil;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;


public class FluxAndMonoSchedulerService {

    private Flux<String> namesFlux1 = Flux.fromIterable(List.of("Andy", "Kobe", "James", "Zack"));
    private Flux<String> namesFlux2 = Flux.fromIterable(List.of("Bella", "Howard", "Kim", "Annie"));

    public Flux<String> namesFlux_block() {
        var flux1 = namesFlux1
                .map(this::toUpperCase);
        var flux2 = namesFlux2
                .map(this::toUpperCase);
        return flux1.mergeWith(flux2);
    }

    public Flux<String> namesFlux_publishOn() {
        var flux1 = namesFlux1
                .subscribeOn(Schedulers.parallel())
                .map(this::toUpperCase);
        var flux2 = namesFlux2
                .publishOn(Schedulers.parallel())
                .map(this::toUpperCase);
        return flux1.mergeWith(flux2);
    }


    public Flux<String> namesFlux_subscribeOn() {
        var flux1 = getStringFlux1().subscribeOn(Schedulers.boundedElastic());
        var flux2 = getStringFlux2().subscribeOn(Schedulers.boundedElastic());
        return flux1.mergeWith(flux2);
    }

    private Flux<String> getStringFlux2() {
        return namesFlux2
                .map(this::toUpperCase);
    }

    private Flux<String> getStringFlux1() {
        return namesFlux1
                .map(this::toUpperCase);
    }

    private String toUpperCase(String name) {
        CommonUtil.delay(1000);
        return name.toUpperCase();
    }


}
