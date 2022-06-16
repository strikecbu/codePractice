package com.cloud.userws.ui.controllers;

import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@RestController
@RequestMapping("/users")
public class UserController {


    private final Environment environment;
    private final Sinks.Many<String> status = Sinks.many()
            .replay()
            .latest();

    public UserController(Environment environment) {
        this.environment = environment;
    }

    public Sinks.Many<String> getStatus() {
        return status;
    }

    @GetMapping(value = "/status/check/stream", produces = {MediaType.APPLICATION_NDJSON_VALUE})
    public Flux<String> healthCheckStream() {
        return status.asFlux();
    }
    @GetMapping(value = "/status/check")
    public Mono<String> healthCheck() {
        return Mono.just("Working on port: " + environment.getProperty("local.server.port"));
    }
}
