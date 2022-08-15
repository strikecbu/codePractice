package com.learnkafka.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

import java.util.List;

@Configuration
@EnableKafka
@Slf4j
public class KafkaConsumerConfig {

    private final KafkaProperties properties;

    public KafkaConsumerConfig(KafkaProperties properties) {
        this.properties = properties;
    }

    private DefaultErrorHandler commonErrorHandler() {
        FixedBackOff backOff = new FixedBackOff(1000L, 2);
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(backOff);
        errorHandler.setRetryListeners((record, ex, deliveryAttempt) -> {
            log.info("Error occurred, ex: {}, attempt: {}.", ex.getMessage(), deliveryAttempt);
        });

        List<Class<? extends Exception>> exList = List.of(IllegalArgumentException.class);
//        exList.forEach(errorHandler::addNotRetryableExceptions);
        exList.forEach(errorHandler::addRetryableExceptions);

        return errorHandler;
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ObjectProvider<ConsumerFactory<Object, Object>> kafkaConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory,
                kafkaConsumerFactory.getIfAvailable(() -> new DefaultKafkaConsumerFactory<>(this.properties.buildConsumerProperties())));
        factory.getContainerProperties()
                .setAckMode(ContainerProperties.AckMode.MANUAL);
        factory.setConcurrency(3);
        factory.setCommonErrorHandler(commonErrorHandler());
        return factory;
    }
}
