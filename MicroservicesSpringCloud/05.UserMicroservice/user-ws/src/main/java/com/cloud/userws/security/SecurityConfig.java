package com.cloud.userws.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${gateway.ip}")
    private String gatewayIp;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
     public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity, SecurityManagement management, SecurityRepository repository) {

        return httpSecurity
                .exceptionHandling()
                .authenticationEntryPoint(management::handleAuthentication)
                .accessDeniedHandler(management::handleAccessDenied)
                .and()
                .authorizeExchange()
                .pathMatchers("/auth/**")
                .permitAll()
                .pathMatchers("/users/**")
                .authenticated()
//                .hasIpAddress(gatewayIp)
                .and()
                .authenticationManager(management)
                .securityContextRepository(repository)
                .csrf()
                .disable()
                .build();

    }


}
