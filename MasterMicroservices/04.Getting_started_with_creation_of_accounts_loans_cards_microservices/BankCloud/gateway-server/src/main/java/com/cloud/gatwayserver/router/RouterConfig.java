package com.cloud.gatwayserver.router;

import com.cloud.gatwayserver.filter.AuthorizationFilterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class RouterConfig {

    @Autowired
    private AuthorizationFilterFactory filterFactory;

    @Bean
    public RouteLocator route(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(spec -> spec
                        .path("/cloudbank/accounts/customers/hello")
                        .filters(filterSpec -> filterSpec
                                .filters(filterFactory.apply(new AuthorizationFilterFactory.Config()))
                                .rewritePath("/cloudbank/accounts/(?<somePath>.*)", "/${somePath}")
                        )
                        .uri("lb://ACCOUNTS"))
                .route(spec -> spec
                        .path("/cloudbank/accounts/**")
                        .filters(filterSpec -> filterSpec
                                .rewritePath("/cloudbank/accounts/(?<somePath>.*)", "/${somePath}")
                                .addResponseHeader("X-Response-Timestamp", new Date().toString()))
                        .uri("lb://ACCOUNTS"))
                .route(spec -> spec
                        .path("/cloudbank/cards/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec
                                .rewritePath("/cloudbank/cards/(?<path>.*)", "/${path}")
                                .addResponseHeader("X-Response-Timestamp", new Date().toString()))
                        .uri("lb://CARDS")
                )
                .route(spec -> spec
                        .path("/cloudbank/loans/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec
                                .rewritePath("/cloudbank/loans/(?<path>.*)", "/${path}")
                                .addResponseHeader("X-Response-Timestamp", new Date().toString()))
                        .uri("lb://LOANS")
                )
                .build();
    }
}
