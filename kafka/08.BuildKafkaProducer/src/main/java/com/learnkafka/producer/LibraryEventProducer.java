package com.learnkafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnkafka.domain.LibraryEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import reactor.kafka.sender.SenderRecord;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@Slf4j
public class LibraryEventProducer {

    private final KafkaTemplate<Integer, String> template;
    private final ObjectMapper objectMapper;
    @Value("${spring.kafka.template.default-topic}")
    private String topicName;

    public LibraryEventProducer(KafkaTemplate<Integer, String> template, ObjectMapper objectMapper) {
        this.template = template;
        this.objectMapper = objectMapper;
    }


    public void sendMessageWithFuture(LibraryEvent event) throws JsonProcessingException {


        String libraryEvent = objectMapper.writeValueAsString(event);
        ListenableFuture<SendResult<Integer, String>> future = template.sendDefault(libraryEvent);
        future.addCallback(result -> {
            RecordMetadata metadata = result.getRecordMetadata();
            log.info("Send event success, key: {}, value: {}, topic: {}",
                    event.getEventId(),
                    libraryEvent,
                    metadata.topic());
        }, ex -> {
            log.error("Send fail, ex: {}", ex.getMessage());
        });
    }

    public SendResult<Integer, String> sendMessageWithSync(LibraryEvent event) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        String libraryEvent = objectMapper.writeValueAsString(event);
        return template.sendDefault(libraryEvent)
                .get(5, TimeUnit.SECONDS);
    }

    public SendResult<Integer, String> sendMessageWithRecordSync(LibraryEvent event) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        ProducerRecord<Integer, String> producerRecord = mapperRecord(event);
        return template.send(producerRecord)
                .get(5, TimeUnit.SECONDS);
    }

    public ProducerRecord<Integer, String> mapperRecord(LibraryEvent event) throws JsonProcessingException {
        String libraryEvent = objectMapper.writeValueAsString(event);
        log.info("produce event string: {}", libraryEvent);
        List<Header> headers = List.of(new RecordHeader("mySpecialKey",
                "Hello".getBytes(StandardCharsets.UTF_8)));
        return new ProducerRecord<>(topicName,
                null,
                event.getEventId(),
                libraryEvent,
                headers);
    }

    public SenderRecord<Integer, String, LibraryEvent> mapperSenderRecord(LibraryEvent event) throws JsonProcessingException {
        ProducerRecord<Integer, String> producerRecord = mapperRecord(event);
        return SenderRecord.create(producerRecord, event);
    }
}
