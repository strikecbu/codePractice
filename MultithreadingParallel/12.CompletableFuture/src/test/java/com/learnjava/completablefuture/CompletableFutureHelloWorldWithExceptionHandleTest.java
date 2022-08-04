package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompletableFutureHelloWorldWithExceptionHandleTest {

    @Mock
    private HelloWorldService hws;

    @Test
    void catch_exception_use_handle_normal() {
        when(hws.hello()).thenCallRealMethod();
        when(hws.world()).thenCallRealMethod();

        CompletableFutureHelloWorldWithExceptionHandle service = new CompletableFutureHelloWorldWithExceptionHandle(hws);
        String greetings = service.catch_exception_use_handle()
                .join();

        assertEquals("HELLO WORLD! HAPPY DAY!", greetings);
    }
    @Test
    void catch_exception_use_handle_error() {
        when(hws.hello()).thenThrow(new RuntimeException("Hello is not coming"));
        when(hws.world()).thenThrow(new RuntimeException("World is change!"));

        CompletableFutureHelloWorldWithExceptionHandle service = new CompletableFutureHelloWorldWithExceptionHandle(hws);
        String greetings = service.catch_exception_use_handle()
                .join();

        assertEquals(" HAPPY DAY!", greetings);
    }
    @Test
    void catch_exception_use_exceptionally_error() {
        when(hws.hello()).thenThrow(new RuntimeException("Hello is not coming"));
        when(hws.world()).thenCallRealMethod();
//        when(hws.world()).thenThrow(new RuntimeException("World is change!"));

        CompletableFutureHelloWorldWithExceptionHandle service = new CompletableFutureHelloWorldWithExceptionHandle(hws);
        String greetings = service.catch_exception_use_exceptionally()
                .join();

        assertEquals(" HAPPY DAY!", greetings);
    }
    @Test
    void catch_exception_use_whenComplete_error() {
        when(hws.hello()).thenThrow(new RuntimeException("Hello is not coming"));
        when(hws.world()).thenCallRealMethod();
//        when(hws.world()).thenThrow(new RuntimeException("World is change!"));

        CompletableFutureHelloWorldWithExceptionHandle service = new CompletableFutureHelloWorldWithExceptionHandle(hws);

        String greetings = service.catch_exception_use_complete()
                .join();

        assertEquals(" HAPPY DAY!", greetings);
    }
}
