package com.learnkafka.handler;

import com.learnkafka.domain.LibraryEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class LibraryEventHandler {

    public Mono<ServerResponse> postEvent(ServerRequest request) {
        return request.bodyToMono(LibraryEvent.class)
                //TODO invoke kafka
                .flatMap(event -> {
                    return ServerResponse.created(URI.create("/libraryEvents"))
                            .bodyValue(event);
                });
    }

    public Mono<ServerResponse> putEvent(ServerRequest request) {
        return Mono.empty();
    }
}
