package com.eazybytes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                        .requestMatchers("/myAccount","/myBalance","/myLoans","/myCards").authenticated()
                        .requestMatchers("/notices","/contact").permitAll()
                .and().formLogin()
                .and().httpBasic();
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager manageUser() {
        UserDetails admin = User.withUsername("admin")
                .password("12345")
                .authorities("admin")
                .build();
        UserDetails user = User.withUsername("user")
                .password("12345")
                .authorities("user")
                .build();
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("12345")
//                .authorities("admin")
//                .build();
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("12345")
//                .authorities("user")
//                .build();



        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }



}
