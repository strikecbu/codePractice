package com.cloud.gatwayserver.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Slf4j
public class CorrelationKeyFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest()
                .getHeaders();
        String key = "cloudbank-correlation-key";
        if (headers.containsKey(key)) {
            String keyValue = headers.getFirst(key);
            log.info("There have correlation-key: {}", keyValue);
        } else {
            String uuid = UUID.randomUUID()
                    .toString();
            // request 是read only，所以必須重新構建request and exchange
            ServerWebExchange webExchange = exchange.mutate()
                    .request(builder -> builder.header(key, uuid)
                            .build())
                    .build();
            return chain.filter(webExchange);
        }
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // do something to response
        }));
    }
}
