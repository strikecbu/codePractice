package com.learnreactiveprogramming;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class BackpressureTest {

    @Test
    void testBackpressure() {
        Flux<Integer> integerFlux = Flux.range(1, 100)
                .log();

        integerFlux.subscribe(new BaseSubscriber<>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(3);
            }

            @Override
            protected void hookOnNext(Integer value) {
                log.info("Number: {}", value);
                if (value == 3) {
                    cancel();
                }
            }

            @Override
            protected void hookOnComplete() {
                super.hookOnComplete();
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                super.hookOnError(throwable);
            }

            @Override
            protected void hookOnCancel() {
                log.info("inside cancel now");
            }
        });

    }

    /**
     * 一次處理五筆資料，最多處理20筆資料就結束
     */
    @Test
    void testBackpressure_backpressureDrop() throws InterruptedException {
        Flux<Integer> integerFlux =
                Flux.range(1, 100)
                        .onBackpressureDrop(number -> {
                            log.info("backpressure drop: {}", number);
                        })
                        .log();

        CountDownLatch countDownLatch = new CountDownLatch(1);
        integerFlux.subscribe(new BaseSubscriber<>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(5);
            }

            @Override
            protected void hookOnNext(Integer value) {
                log.info("Number: {}", value);
                if (value % 5 == 0 && value < 20) {
                    request(5);
                } else if (value == 20) {
                    hookOnComplete();
//                    cancel();
                }
            }

            @Override
            protected void hookOnComplete() {
                log.info("inside complete now");
                countDownLatch.countDown();
//                super.hookOnComplete();
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                super.hookOnError(throwable);
            }

            @Override
            protected void hookOnCancel() {
                log.info("inside cancel now");
            }

        });

        countDownLatch.await(5, TimeUnit.SECONDS);

    }

    @Test
    void testBackpressure_backpressureBuffer() throws InterruptedException {
        Flux<Integer> integerFlux =
                Flux.range(1, 100)
                        .onBackpressureBuffer(10, number -> {
                            log.info("backpressure buffer last is : {}", number);
                        })
                        .log();

        CountDownLatch countDownLatch = new CountDownLatch(1);
        integerFlux.subscribe(new BaseSubscriber<>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(5);
            }

            @Override
            protected void hookOnNext(Integer value) {
                log.info("Number: {}", value);
                if (value % 5 == 0 && value < 20) {
                    request(5);
                } else if (value == 20) {
                    hookOnCancel();
//                    cancel();
                }
            }

            @Override
            protected void hookOnComplete() {
                log.info("inside complete now");

//                super.hookOnComplete();
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                super.hookOnError(throwable);
            }

            @Override
            protected void hookOnCancel() {
                log.info("inside cancel now");
                countDownLatch.countDown();
            }

        });

        countDownLatch.await(10, TimeUnit.SECONDS);

    }
    @Test
    void testBackpressure_backpressureError() throws InterruptedException {
        Flux<Integer> integerFlux =
                Flux.range(1, 100)
                        .onBackpressureError()
                        .log();

        CountDownLatch countDownLatch = new CountDownLatch(1);
        integerFlux.subscribe(new BaseSubscriber<>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(5);
            }

            @Override
            protected void hookOnNext(Integer value) {
                log.info("Number: {}", value);
                if (value % 5 == 0 && value < 20) {
                    request(5);
                } else if (value == 20) {
                    hookOnCancel();
//                    cancel();
                }
            }

            @Override
            protected void hookOnComplete() {
                log.info("inside complete now");

//                super.hookOnComplete();
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                log.error("error occurred: ",throwable);
                super.hookOnError(throwable);
            }

            @Override
            protected void hookOnCancel() {
                log.info("inside cancel now");
                countDownLatch.countDown();
            }

        });

        countDownLatch.await(10, TimeUnit.SECONDS);

    }

}
