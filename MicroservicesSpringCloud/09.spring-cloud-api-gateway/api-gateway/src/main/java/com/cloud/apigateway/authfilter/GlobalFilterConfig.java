package com.cloud.apigateway.authfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

@Configuration
public class GlobalFilterConfig {

    private final Logger logger = LoggerFactory.getLogger(GlobalFilterConfig.class);

    @Order(1)
    @Bean
    public GlobalFilter secondFilter() {
        return (exchange, chain) -> {
            logger.info("Second pre filter execute!");

            return chain.filter(exchange).then(Mono.fromRunnable(() -> logger.info("Second post filter execute!")));
        };
    }
    @Order(0)
    @Bean
    public GlobalFilter firstFilter() {
        return (exchange, chain) -> {
            logger.info("First pre filter execute!");

            return chain.filter(exchange).then(Mono.fromRunnable(() -> logger.info("First post filter execute!")));
        };
    }
}
