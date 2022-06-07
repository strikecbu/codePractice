package com.reactivespring.route;

import com.reactivespring.handler.ReviewHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    private final ReviewHandler handler;

    public RouterConfig(ReviewHandler handler) {
        this.handler = handler;
    }

    @Bean
    public RouterFunction<ServerResponse> reviewRouter() {
        return route().GET("/v1/hello",
                        (req -> ServerResponse.ok()
                                .bodyValue("Hello World")))
                .nest(path("/v1/reviews"),
                        () -> route()
                                .GET( handler::getAllReviews)
                                .POST(handler::addReview)
                                .PUT("/{id}", handler::updateReview)
                                .DELETE("/{id}", handler::deleteReview)
                                .build())
                .build();
    }
}
