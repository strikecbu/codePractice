package com.cloud.accountws.ui.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @GetMapping("/status/check")
    public Mono<String> checkStatus() {
        return Mono.just("working...");
    }
}
