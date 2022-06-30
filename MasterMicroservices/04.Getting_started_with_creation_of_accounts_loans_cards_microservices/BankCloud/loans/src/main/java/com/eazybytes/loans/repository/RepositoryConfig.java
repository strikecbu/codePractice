package com.eazybytes.loans.repository;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;
import static io.r2dbc.spi.ConnectionFactoryOptions.DATABASE;

@Configuration
@EnableR2dbcAuditing
@ConfigurationProperties(prefix = "db.postgres")
@Data
public class RepositoryConfig {

    private String url;
    private String database;
    private String username;
    private String password;


    @Bean
    ConnectionFactory connectionFactory() {
        return ConnectionFactoryBuilder
                .withUrl(url)
                .database(database)
                .username(username)
                .password(password)
                .configure(option -> {
                    option.option(Option.valueOf("schema"), "public");
                })
                .build();

//        return ConnectionFactories.get(ConnectionFactoryOptions.builder()
//                .option(DRIVER, "postgresql")
//                .option(HOST, "localhost")
//                .option(PORT, 5432)  // optional, defaults to 5432
//                .option(USER, "postgres")
//                .option(PASSWORD, "p@ssw0rd")
//                .option(DATABASE, "postgres")  // optional
//                .option(Option.valueOf("schema"), "public")
//                .build());
    }
}
