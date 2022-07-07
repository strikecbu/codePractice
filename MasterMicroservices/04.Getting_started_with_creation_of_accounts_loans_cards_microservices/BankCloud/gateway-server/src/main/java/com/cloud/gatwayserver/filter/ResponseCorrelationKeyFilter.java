package com.cloud.gatwayserver.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class ResponseCorrelationKeyFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String key = "cloudbank-correlation-key";
        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    String correlationKey = exchange.getRequest()
                            .getHeaders()
                            .getFirst(key);
                    exchange.getResponse().getHeaders()
                            .set(key, correlationKey);
                }));
    }
}
