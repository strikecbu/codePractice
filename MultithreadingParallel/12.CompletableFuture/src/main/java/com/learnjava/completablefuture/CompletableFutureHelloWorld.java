package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.LoggerUtil.log;


public class CompletableFutureHelloWorld {

    private HelloWorldService hws;

    public CompletableFutureHelloWorld(HelloWorldService hws) {
        this.hws = hws;
    }

    public CompletableFuture<String> helloWorld() {
        return CompletableFuture.supplyAsync(hws::helloWorld);
    }

    public CompletableFuture<String> combineAndApplyHelloWorld() {
        CompletableFuture<String> helloFuture = CompletableFuture.supplyAsync(hws::hello);
        CompletableFuture<String> worldFuture = CompletableFuture.supplyAsync(hws::world);
        CompletableFuture<String> happyFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Happy day!";
        });
        return helloFuture
                .thenCombineAsync(worldFuture, (s, s2) -> s + s2)
                .thenCombineAsync(happyFuture, (s, s2) -> s + s2)
                .thenApply(String::toUpperCase);

    }
    public CompletableFuture<String> combineAndApplyHelloWorld_log() {
        CompletableFuture<String> helloFuture = CompletableFuture.supplyAsync(hws::hello);
        CompletableFuture<String> worldFuture = CompletableFuture.supplyAsync(hws::world);
        CompletableFuture<String> happyFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Happy day!";
        });
        return helloFuture
                .thenCombine(worldFuture, (s, s2) -> {
                    log("Combine h/w");
                    return s + s2;
                })
                .thenCombine(happyFuture, (s, s2) -> {
                    log("Combine happy day");
                    return s + s2;
                })
                .thenApply(s -> {
                    log("Apply uppercase");
                    return s.toUpperCase();
                });

    }
    public CompletableFuture<String> combineAndApplyHelloWorld_own_threadPool() {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
                .availableProcessors());
        CompletableFuture<String> helloFuture = CompletableFuture.supplyAsync(hws::hello, executorService);
        CompletableFuture<String> worldFuture = CompletableFuture.supplyAsync(hws::world, executorService);
        CompletableFuture<String> happyFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Happy day!";
        }, executorService);
        return helloFuture
                .thenCombine(worldFuture, (s, s2) -> {
                    log("Combine h/w");
                    return s + s2;
                })
                .thenCombine(happyFuture, (s, s2) -> {
                    log("Combine happy day");
                    return s + s2;
                })
                .thenApply(s -> {
                    log("Apply uppercase");
                    return s.toUpperCase();
                });

    }
    public CompletableFuture<String> composeHelloWorld() {
        CompletableFuture<String> helloFuture = CompletableFuture.supplyAsync(hws::hello);
        return helloFuture
                .thenCompose(hws::worldFuture);
    }

    public static void main(String[] args) {
        HelloWorldService hws = new HelloWorldService();
        CompletableFuture.supplyAsync(hws::helloWorld)
                .thenAccept(result -> {
                    log("Result is " + result);
                })
                .join();

        log("Done!");
//        delay(2000);

    }
}
