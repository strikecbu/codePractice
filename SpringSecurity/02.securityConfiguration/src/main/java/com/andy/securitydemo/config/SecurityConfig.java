package com.andy.securitydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
                .pathMatchers(
                        "/myAccount",
                        "/myBalance",
                        "/myCards",
                        "/myLoans"
                )
                .authenticated()
                .pathMatchers(
                        "/contact",
                        "/notices"
                )
                .permitAll()
                .and()
                .formLogin()
                .and()
                .httpBasic();
        return http.build();
    }

}
