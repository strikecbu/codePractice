package com.cloud.userws.ui.handlers;

import com.cloud.userws.domain.UserEntity;
import com.cloud.userws.mapper.UserMapper;
import com.cloud.userws.security.TokenProvider;
import com.cloud.userws.services.UserService;
import com.cloud.userws.ui.model.LoginRequest;
import com.cloud.userws.ui.model.UserRequestModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.net.URI;
import java.util.Set;

@Controller
public class SecurityHandler {

    private final BCryptPasswordEncoder encoder;
    private final UserService userService;
    private final TokenProvider tokenProvider;

    private final UserMapper userMapper;

    private final Validator validator;


    public SecurityHandler(BCryptPasswordEncoder encoder, UserService userService, TokenProvider tokenProvider,
                           UserMapper userMapper, Validator validator) {
        this.encoder = encoder;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.userMapper = userMapper;
        this.validator = validator;
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

    public Mono<ServerResponse> signup(ServerRequest request) {
        return request.bodyToMono(UserRequestModel.class)
                .doOnNext(this::validateRequest)
                .map(userMapper::requestToDto)
                .flatMap(userService::createUser)
                .map(userMapper::entityToResponse)
                .flatMap(user -> ServerResponse.created(URI.create("/users"))
                        .bodyValue(user));
    }

    private void validateRequest(UserRequestModel requestModel) {
        Set<ConstraintViolation<UserRequestModel>> violationSet = validator.validate(requestModel);
        if (violationSet.size() > 0) {
            throw new IllegalArgumentException("Bad request");
        }
    }

}
