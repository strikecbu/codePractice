package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.LoggerUtil.log;


public class CompletableFutureHelloWorldWithExceptionHandle {

    private HelloWorldService hws;

    public CompletableFutureHelloWorldWithExceptionHandle(HelloWorldService hws) {
        this.hws = hws;
    }

    public CompletableFuture<String> catch_exception_use_handle() {
        CompletableFuture<String> helloFuture = CompletableFuture.supplyAsync(hws::hello);
        CompletableFuture<String> worldFuture = CompletableFuture.supplyAsync(hws::world);
        CompletableFuture<String> happyFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Happy day!";
        });
        return helloFuture
                .handle((res, ex) -> {
                    log("res is: " + res);
                    if (ex != null) {
                        log("Exception happen: " + ex.getMessage());
                        return "";
                    } else {
                        return res;
                    }
                })
                .thenCombineAsync(worldFuture, (s, s2) -> s + s2)
                .handle((result, ex) -> {
                    log("result is: " + result);
                    if (ex != null) {
                        log("Exception after hello happen: " + ex.getMessage());
                        return "";
                    } else {
                        return result;
                    }
                })
                .thenCombineAsync(happyFuture, (s, s2) -> s + s2)
                .thenApply(String::toUpperCase);

    }

    public CompletableFuture<String> catch_exception_use_exceptionally() {
        CompletableFuture<String> helloFuture = CompletableFuture.supplyAsync(hws::hello);
        CompletableFuture<String> worldFuture = CompletableFuture.supplyAsync(hws::world);
        CompletableFuture<String> happyFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            log("inside happy");
            return " Happy day!";
        });
        return helloFuture
                .exceptionally(ex -> {
                    log("Exception happen: " + ex.getMessage());
                    return "";
                })
                .thenCombine(worldFuture, (s, s2) -> s + s2)
                .exceptionally(ex -> {
                    log("Exception after hello happen: " + ex.getMessage());
                    return "";
                })
                .thenCombine(happyFuture, (s, s2) -> s + s2)
                .thenApply(value -> {
                    log("inside uppercase");
                    return value.toUpperCase();
                });

    }

    public CompletableFuture<String> catch_exception_use_complete() {
        CompletableFuture<String> helloFuture = CompletableFuture.supplyAsync(hws::hello);
        CompletableFuture<String> worldFuture = CompletableFuture.supplyAsync(hws::world);
        CompletableFuture<String> happyFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            log("inside happy");
            return " Happy day!";
        });
        return helloFuture
                .whenComplete((res, ex) -> {
                    log("res is: " + res);
                    if (ex != null) {
                        log("Exception happen: " + ex.getMessage());
                    }
                })
                .thenCombine(worldFuture, (s, s2) ->{
                    log("Execute thenCombineAsync after hello");
                    return  s + s2;
                })
                .whenComplete((res, ex) -> {
                    log("result is: " + res);
                    if (ex != null) {
                        log("Exception after hello happen: " + ex.getMessage());
                    }
                })
                .exceptionally(ex -> {
                    log("Exception after hello world happen: " + ex.getMessage());
                    return "";
                })
                .thenCombine(happyFuture, (s, s2) -> s + s2)
                .thenApply(String::toUpperCase);

    }
}
