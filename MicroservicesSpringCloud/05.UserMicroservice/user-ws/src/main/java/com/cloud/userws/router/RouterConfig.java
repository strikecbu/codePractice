package com.cloud.userws.router;

import com.cloud.userws.ui.handlers.SecurityHandler;
import com.cloud.userws.ui.handlers.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;

@Configuration
public class RouterConfig {


    private final UserHandler userHandler;
    private final SecurityHandler securityHandler;

    public RouterConfig(UserHandler userHandler, SecurityHandler securityHandler) {
        this.userHandler = userHandler;
        this.securityHandler = securityHandler;
    }


    @Bean
    public RouterFunction<ServerResponse> userRoute() {
        return RouterFunctions.route()
                .nest(path("/users"), builder ->
                        builder.GET("/status/check", userHandler::statusCheck)
                                .GET("/{userId}", userHandler::getUserById))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> authRoute() {
        return RouterFunctions.route()
                .nest(path("/auth"), builder ->
                        builder.POST("/login", securityHandler::login))
                .build();
    }


}
