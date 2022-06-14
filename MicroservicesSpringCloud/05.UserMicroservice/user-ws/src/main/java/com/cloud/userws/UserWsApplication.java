package com.cloud.userws;

import com.cloud.userws.ui.controllers.UserController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableEurekaClient
public class UserWsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserWsApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserController userController) {
        return args -> {
            Flux.interval(Duration.ofSeconds(1))
                    .doOnNext(index -> {
                        userController.getStatus().tryEmitNext("Check status at " + LocalDateTime.now()+ "\n");
                    })
                    .subscribe();
        };
    }

}
