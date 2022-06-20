package com.cloud.apigateway.authfilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Objects;

@Component
public class AuthorizationHeader extends AbstractGatewayFilterFactory<AuthorizationHeader.Config> {

    @Value("${token.secret}")
    private String secret;

    public AuthorizationHeader() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (!request.getHeaders()
                    .containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }
            String authToken = Objects.requireNonNull(request.getHeaders()
                            .getFirst(HttpHeaders.AUTHORIZATION))
                    .replace("Bearer ", "");
            if (authToken.isBlank()) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

            String subject;
            try {
                subject = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(authToken)
                        .getBody()
                        .getSubject();

            } catch (Exception ex) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            if (subject == null || subject.isBlank()) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus unauthorized) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(unauthorized);
        return response.setComplete();
    }

    public static class Config {

    }
}
