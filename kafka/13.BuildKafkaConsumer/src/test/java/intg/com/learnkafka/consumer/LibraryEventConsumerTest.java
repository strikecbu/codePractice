package com.learnkafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnkafka.entity.Book;
import com.learnkafka.entity.LibraryEvent;
import com.learnkafka.entity.LibraryEventType;
import com.learnkafka.repository.LibraryEventRepository;
import com.learnkafka.service.LibraryEventService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles(profiles = {"test"})
@EmbeddedKafka(topics = {"${spring.kafka.template.default-topic}"}, partitions = 3)
@TestPropertySource(properties = {
        "spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.consumer.bootstrap-servers=${spring.embedded.kafka.brokers}",
})
class LibraryEventConsumerTest {

    @Autowired
    private KafkaTemplate<Integer, String> template;

    @Autowired
    private EmbeddedKafkaBroker broker;

    @Autowired
    private KafkaListenerEndpointRegistry registry;

    @Autowired
    private LibraryEventRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private LibraryEventConsumer consumer;
    @SpyBean
    private LibraryEventService service;

    @BeforeEach
    void setUp() {
        for (MessageListenerContainer container : registry.getAllListenerContainers()) {
            ContainerTestUtils.waitForAssignment(container, broker.getPartitionsPerTopic());
        }
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void send_new_library_event() throws ExecutionException, InterruptedException, JsonProcessingException {
        String json = "{\"eventId\":null,\"eventType\":\"NEW\",\"book\":{\"id\":1233,\"name\":\"Elder Ring\",\"authorName\":\"Andy\"}}";
        template.sendDefault(json)
                .get();

        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await(3, TimeUnit.SECONDS);

        verify(consumer, times(1)).consumer(isA(ConsumerRecord.class));
        verify(service, times(1)).processEvent(isA(ConsumerRecord.class));
        assert repository.findById(1)
                .isPresent();
    }

    @Test
    void send_update_library_event() throws ExecutionException, InterruptedException, JsonProcessingException {
        Book book = Book.builder()
                .authorName("Andy")
                .name("Elder Ring")
                .id(123)
                .build();
        LibraryEvent libraryEvent = LibraryEvent.builder()
                .eventType(LibraryEventType.NEW)
                .book(book)
                .build();
        repository.save(libraryEvent);

        String updateBookName = "Elder Ring V2";
        libraryEvent.getBook()
                .setName(updateBookName);
        String json = objectMapper.writeValueAsString(libraryEvent);

        template.sendDefault(libraryEvent.getEventId(), json)
                .get();

        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await(3, TimeUnit.SECONDS);

        verify(consumer, times(1)).consumer(isA(ConsumerRecord.class));
        verify(service, times(1)).processEvent(isA(ConsumerRecord.class));
        LibraryEvent event = repository.findById(libraryEvent.getEventId())
                .get();

        assertEquals(updateBookName,
                event.getBook()
                        .getName());

    }

    @Test
    void send_invalidate_update_library_event() throws ExecutionException, InterruptedException, JsonProcessingException {
        String json = "{\"eventId\":1,\"eventType\":\"UPDATE\",\"book\":{\"id\":1233,\"name\":\"Elder Ring V2\",\"authorName\":\"Andy\"}}";

        template.sendDefault(1, json)
                .get();

        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await(5, TimeUnit.SECONDS);

        verify(consumer, times(3)).consumer(isA(ConsumerRecord.class));
        verify(service, times(3)).processEvent(isA(ConsumerRecord.class));

    }
}
