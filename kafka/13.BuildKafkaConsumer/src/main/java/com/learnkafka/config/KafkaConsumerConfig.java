package com.learnkafka.config;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    private final KafkaProperties properties;

    public KafkaConsumerConfig(KafkaProperties properties) {
        this.properties = properties;
    }

//    @Bean
//    ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
//            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
//            ObjectProvider<ConsumerFactory<Object, Object>> kafkaConsumerFactory) {
//        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        configurer.configure(factory,
//                kafkaConsumerFactory.getIfAvailable(() -> new DefaultKafkaConsumerFactory<>(this.properties.buildConsumerProperties())));
//        factory.getContainerProperties()
//                .setAckMode(ContainerProperties.AckMode.MANUAL);
//        factory.setConcurrency(3);
//        return factory;
//    }
}
