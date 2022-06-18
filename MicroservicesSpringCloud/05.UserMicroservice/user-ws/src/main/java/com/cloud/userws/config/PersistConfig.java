package com.cloud.userws.config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@Configuration
@EnableR2dbcAuditing
public class PersistConfig {

    @Bean
    ConnectionFactory connectionFactory() {
        return ConnectionFactories.get(ConnectionFactoryOptions.builder()
                .option(DRIVER, "postgresql")
                .option(HOST, "localhost")
                .option(PORT, 5432)  // optional, defaults to 5432
                .option(USER, "postgres")
                .option(PASSWORD, "p@ssw0rd")
                .option(DATABASE, "postgres")  // optional
                .option(Option.valueOf("schema"), "public")
                .build());
    }

    @Bean
    R2dbcEntityTemplate r2dbcEntityTemplate(ConnectionFactory connectionFactory) {
        return new R2dbcEntityTemplate(connectionFactory);
    }
}
