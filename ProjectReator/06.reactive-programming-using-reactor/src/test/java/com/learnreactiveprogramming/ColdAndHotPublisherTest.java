package com.learnreactiveprogramming;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.Disposable;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

import static com.learnreactiveprogramming.util.CommonUtil.delay;

@Slf4j
public class ColdAndHotPublisherTest {

    @Test
    void simple_cold() {
        Flux<Integer> range = Flux.range(0, 10);

        range.subscribe((i) -> {
            log.info("Subscribe value: {}", i);
        });
        range.subscribe((i) -> {
            log.info("Subscribe1 value: {}", i);
        });
    }
    @Test
    void simple_hot() {
        ConnectableFlux<Integer> publish = Flux.range(0, 10)
                .delayElements(Duration.ofSeconds(1))
                .publish();
        publish.connect();

        publish.subscribe((i) -> {
            log.info("Subscribe value: {}", i);
        });
        delay(2000);
        publish.subscribe((i) -> {
            log.info("Subscribe1 value: {}", i);
        });

        delay(10000);
    }
    @Test
    void simple_hot_auto() {
        Flux<Integer> integerFlux = Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1));
        Flux<Integer> flux = integerFlux.publish()
                .autoConnect(2);

        Disposable subscribe1 = flux.subscribe((i) -> {
            log.info("Subscribe1 value: {}", i);
        });
        delay(2000);
        Disposable subscribe2 = flux.subscribe((i) -> {
            log.info("Subscribe2 value: {}", i);
        });
        delay(2000);
        subscribe1.dispose();
        subscribe2.dispose();
        flux.subscribe((i) -> {
            log.info("Subscribe3 value: {}", i);
        });
        delay(1000);
        flux.subscribe((i) -> {
            log.info("Subscribe4 value: {}", i);
        });

        delay(10000);
    }
    @Test
    void simple_hot_ref() {
        Flux<Integer> integerFlux = Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1));
        Flux<Integer> flux = integerFlux.publish()
                .refCount(2)
                .doOnCancel(() -> {
                    log.info("Cancel occurred.");
                });

        Disposable subscribe1 = flux.subscribe((i) -> {
            log.info("Subscribe1 value: {}", i);
        });
        delay(2000);
        Disposable subscribe2 = flux.subscribe((i) -> {
            log.info("Subscribe2 value: {}", i);
        });
        delay(2000);
        subscribe1.dispose();
        subscribe2.dispose();
        flux.subscribe((i) -> {
            log.info("Subscribe3 value: {}", i);
        });
        delay(1000);
        flux.subscribe((i) -> {
            log.info("Subscribe4 value: {}", i);
        });

        delay(10000);
    }

}
