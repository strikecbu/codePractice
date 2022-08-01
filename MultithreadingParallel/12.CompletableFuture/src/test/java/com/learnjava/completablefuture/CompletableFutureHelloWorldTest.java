package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;
import org.junit.jupiter.api.Test;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CompletableFutureHelloWorldTest {
    HelloWorldService helloWorldService = new HelloWorldService();
    CompletableFutureHelloWorld futureHelloWorld = new CompletableFutureHelloWorld(helloWorldService);

    @Test
    void helloWorld() {
        futureHelloWorld.helloWorld()
                .thenAccept(result -> {
                    assertEquals("hello world", result);
                })
                .join();
    }

    @Test
    void combineAndApplyHelloWorld() {
        startTimer();
        futureHelloWorld.combineAndApplyHelloWorld()
                .thenAccept(result -> {
                    assertEquals("HELLO WORLD! HAPPY DAY!", result);
                })
                .join();
        timeTaken();
    }

    @Test
    void composeHelloWorld() {
        startTimer();
        futureHelloWorld.composeHelloWorld()
                .thenAccept(result -> {
                    assertEquals("hello world!", result);
                })
                .join();
        timeTaken();
    }
}
