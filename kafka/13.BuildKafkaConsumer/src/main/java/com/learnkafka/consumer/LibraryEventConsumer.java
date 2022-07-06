package com.learnkafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learnkafka.service.LibraryEventService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

 @Component
@Slf4j
public class LibraryEventConsumer {

     private final LibraryEventService service;

     public LibraryEventConsumer(LibraryEventService service) {
         this.service = service;
     }

     @KafkaListener(topics = {"library-events"})
    public void consumer(ConsumerRecord<Integer, String> record) throws JsonProcessingException {
        log.info("consumer record: {}", record);
        service.processEvent(record);
    }

}
