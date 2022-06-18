package com.cloud.userws.ui.controllers;

import com.cloud.userws.domain.UserEntity;
import com.cloud.userws.mapper.UserMapper;
import com.cloud.userws.services.UserService;
import com.cloud.userws.ui.model.UserRequestModel;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {


    private final Environment environment;

    private final UserMapper userMapper;

    private final UserService userService;
    private final Sinks.Many<String> status = Sinks.many()
            .replay()
            .latest();

    public UserController(Environment environment, UserMapper userMapper, UserService userService) {
        this.environment = environment;
        this.userMapper = userMapper;
        this.userService = userService;
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

    @PostMapping
    public Mono<UserEntity> createUser(@Validated @RequestBody Mono<UserRequestModel> requestModel) {
        return requestModel.map(userMapper::requestToDto)
                .flatMap(userService::createUser);
    }
}
