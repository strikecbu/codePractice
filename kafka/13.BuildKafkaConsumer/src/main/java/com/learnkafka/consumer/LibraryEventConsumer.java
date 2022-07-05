package com.learnkafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

 //@Component
@Slf4j
public class LibraryEventConsumer {

    @KafkaListener(topics = {"library-events"})
    public void consumer(ConsumerRecord<Integer, String> record) {
        log.info("consumer record: {}", record);
    }

}
