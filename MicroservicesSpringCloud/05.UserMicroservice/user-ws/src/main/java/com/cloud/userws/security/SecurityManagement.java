package com.cloud.userws.security;

import com.cloud.userws.repository.UserRepository;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SecurityManagement implements ReactiveAuthenticationManager {

    private final TokenProvider tokenProvider;

    private final UserRepository userRepository;

    public SecurityManagement(TokenProvider tokenProvider, UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }


    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials()
                .toString();
        Claims claims = tokenProvider.parseToUserPublicId(token);
        String publicId = claims.getSubject();
        List<SimpleGrantedAuthority> authorities = ((List<String>) claims.get(TokenProvider.AUTHORITIES_KEY)).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return userRepository.findByPublicId(publicId)
                .map(userEntity -> new UsernamePasswordAuthenticationToken(publicId, userEntity, authorities))
                .doOnNext(authToken -> {
                    SecurityContextHolder.getContext()
                            .setAuthentication(authToken);
                })
                .log()
                .map(authToken -> authToken);
    }

    public Mono<Void> handleAuthentication(ServerWebExchange serverWebExchange, AuthenticationException e) {
        serverWebExchange.getResponse()
                .setStatusCode(HttpStatus.UNAUTHORIZED);
        return Mono.empty();
    }

    public Mono<Void> handleAccessDenied(ServerWebExchange serverWebExchange, AccessDeniedException e) {
        serverWebExchange.getResponse()
                .setStatusCode(HttpStatus.FORBIDDEN);
        return Mono.empty();
    }
}
