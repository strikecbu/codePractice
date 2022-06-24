package com.cloud.userws.ui.handlers;

import com.cloud.userws.mapper.UserMapper;
import com.cloud.userws.repository.UserRepository;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Controller
public class UserHandler {

    private final Environment environment;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserHandler(Environment environment, UserRepository userRepository, UserMapper userMapper) {
        this.environment = environment;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    public Mono<ServerResponse> statusCheck(ServerRequest request) {
        return ServerResponse.ok()
                .bodyValue("Working on port: " + environment.getProperty("local.server.port") + ", title: " +
                        environment.getProperty("ui.title"));
    }

    public Mono<ServerResponse> getUserById(ServerRequest request) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        String userId = request.pathVariable("userId");
        return userRepository.findByPublicId(userId)
                .map(userMapper::entityToResponse)
                .flatMap(resp -> ServerResponse.ok()
                        .bodyValue(resp))
                .switchIfEmpty(ServerResponse.notFound()
                        .build());
    }
}
