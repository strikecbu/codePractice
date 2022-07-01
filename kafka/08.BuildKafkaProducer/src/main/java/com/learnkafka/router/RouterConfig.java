package com.learnkafka.router;

import com.learnkafka.handler.LibraryEventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> libraryEventRoute(LibraryEventHandler handler) {
        return RouterFunctions.route()
                .nest(path("/v1/libraryEvents"), builder ->
                        builder.POST("", handler::postEvent)
                                .PUT("/{eventId}", handler::putEvent))
                .nest(path("/v2/libraryEvents"), builder ->
                        builder.POST("", handler::postEventReactive))
                .build();
    }
}
