package com.cloud.userws.ui.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Sinks.Many<String> status = Sinks.many()
            .replay()
            .latest();

    public Sinks.Many<String> getStatus() {
        return status;
    }

    @GetMapping(value = "/status/check", produces = {MediaType.APPLICATION_NDJSON_VALUE})
    public Flux<String> healthCheck() {
        return status.asFlux();
    }
}
