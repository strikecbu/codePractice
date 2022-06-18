package com.cloud.userws.ui.handlers;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Controller
public class UserHandler {

    private final Environment environment;

    public UserHandler(Environment environment) {
        this.environment = environment;
    }


    public Mono<ServerResponse> statusCheck(ServerRequest request) {
        return ServerResponse.ok()
                .bodyValue("Working on port: " + environment.getProperty("local.server.port"));
    }
}
