package com.learnjava.completablefuture;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureAnyOfTest {

    @Test
    void anyOf() {
        CompletableFutureAnyOf service = new CompletableFutureAnyOf();
        String word = service.anyOf();
        assertEquals("hello world", word);
    }
}
