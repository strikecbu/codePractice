package com.andy.securitydemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class WelcomeController {

    @GetMapping("/welcome")
    public Mono<String> greetings() {
        return Mono.just("Hello! this is no security!");
    }
}
