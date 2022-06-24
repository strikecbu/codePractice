package com.cloud.bankconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class BankConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankConfigApplication.class, args);
    }

}
