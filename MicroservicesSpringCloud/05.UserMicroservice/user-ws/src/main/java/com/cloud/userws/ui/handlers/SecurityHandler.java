package com.cloud.userws.ui.handlers;

import com.cloud.userws.domain.UserEntity;
import com.cloud.userws.security.TokenProvider;
import com.cloud.userws.services.UserService;
import com.cloud.userws.ui.model.LoginRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Controller
public class SecurityHandler {

    private final BCryptPasswordEncoder encoder;
    private final UserService userService;
    private final TokenProvider tokenProvider;

    public SecurityHandler(BCryptPasswordEncoder encoder, UserService userService, TokenProvider tokenProvider) {
        this.encoder = encoder;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }


    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginRequest.class)
                .flatMap(req -> {
                    String password = req.getPassword();
                    Mono<UserEntity> byEmail = userService.findUserByEmail(req.getEmail());
                    return byEmail.flatMap(userEntity -> {
                        if (encoder.matches(password, userEntity.getEncryptPassword())) {
                            String token = tokenProvider.token(userEntity);
                            return ServerResponse.ok()
                                    .header("token", token)
                                    .header("user-id", userEntity.getPublicId())
                                    .build();
                        } else {
                            return ServerResponse.badRequest()
                                    .bodyValue("Wrong password");
                        }
                    });
                })
                .switchIfEmpty(ServerResponse.badRequest()
                        .bodyValue("Not found any user!"))
                .log();
    }
}
