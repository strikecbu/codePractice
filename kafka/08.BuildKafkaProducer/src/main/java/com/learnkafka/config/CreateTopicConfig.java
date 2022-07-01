package com.learnkafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class CreateTopicConfig {

    @Bean
    public NewTopic createTopic() {
        return TopicBuilder.name("library-events")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
