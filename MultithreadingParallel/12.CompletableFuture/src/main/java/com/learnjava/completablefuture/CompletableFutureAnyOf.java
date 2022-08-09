package com.learnjava.completablefuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.LoggerUtil.log;

public class CompletableFutureAnyOf {

    public String anyOf() {
        CompletableFuture<String> fromDb = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            log("result from db");
            return "hello world";
        });
        CompletableFuture<String> fromRest = CompletableFuture.supplyAsync(() -> {
            delay(2000);
            log("result from rest call");
            return "hello world";
        });
        CompletableFuture<String> fromSoap = CompletableFuture.supplyAsync(() -> {
            delay(3000);
            log("result from fromSoap call");
            return "hello world";
        });

        List<CompletableFuture<String>> futureList = List.of(fromDb, fromRest, fromSoap);

        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(futureList.toArray(new CompletableFuture[futureList.size()]));
        return anyOf.thenApply(v -> (String) v)
                .join();
    }
}
