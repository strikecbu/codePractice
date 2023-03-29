package com.eazybytes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.cors()
                .configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowCredentials(true);
                    corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                    corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                    corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                    corsConfiguration.setMaxAge(3600L);
                    return corsConfiguration;
                })
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards")
                .authenticated()
                .requestMatchers("/notices", "/contact", "/register")
                .permitAll()
                .and()
                .formLogin()
                .and()
                .httpBasic();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
