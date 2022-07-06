package com.learnkafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnkafka.entity.LibraryEvent;
import com.learnkafka.repository.LibraryEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class LibraryEventService {

    private final LibraryEventRepository repository;
    private final ObjectMapper objectMapper;

    public LibraryEventService(LibraryEventRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    public void processEvent(ConsumerRecord<Integer, String> record) throws JsonProcessingException {
        LibraryEvent libraryEvent = objectMapper.readValue(record.value(), LibraryEvent.class);
        libraryEvent.getBook().setLibraryEvent(libraryEvent);

        switch (libraryEvent.getEventType().toString()) {
            case "NEW":
                repository.save(libraryEvent);
                log.info("Save libraryEvent succeed. event: {}", libraryEvent);
                break;
            case "UPDATE":
                validate(libraryEvent);
                repository.save(libraryEvent);
                log.info("Update libraryEvent succeed, event: {}", libraryEvent);
                break;
            default:
                log.info("invalidate event {}", libraryEvent);
        }
    }

    private void validate(LibraryEvent libraryEvent) {
        if (libraryEvent.getEventId() == null) {
            throw new IllegalArgumentException("No event id provided!");
        }
        Optional<LibraryEvent> eventOptional = repository.findById(libraryEvent.getEventId());
        if (eventOptional.isEmpty()) {
            throw new IllegalArgumentException("Not available event id!");
        }
    }
}
