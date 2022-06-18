package com.cloud.userws.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class SecurityRepository implements ServerSecurityContextRepository {

    private static final String TOKEN_PREFIX = "Bearer ";
    private final SecurityManagement management;

    public SecurityRepository(SecurityManagement management) {
        this.management = management;
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String authHeader = Optional.ofNullable(request.getHeaders()
                        .getFirst(HttpHeaders.AUTHORIZATION))
                .orElse("");
        if (authHeader.startsWith(TOKEN_PREFIX)) {
            String token = authHeader.replaceAll(TOKEN_PREFIX, "");
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    token,
                    token);

            return management.authenticate(auth)
                    .map(SecurityContextImpl::new);
        }
        return Mono.empty();
    }
}
