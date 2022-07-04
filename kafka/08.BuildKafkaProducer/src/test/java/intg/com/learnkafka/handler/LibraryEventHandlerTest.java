package com.learnkafka.handler;

import com.learnkafka.config.KafkaConfig;
import com.learnkafka.domain.Book;
import com.learnkafka.domain.LibraryEvent;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(topics = {"${spring.kafka.template.default-topic}"}, partitions = 3)
@TestPropertySource(properties = {
        "spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.admin.properties.bootstrap.servers=${spring.embedded.kafka.brokers}"
})
@AutoConfigureWebTestClient
class LibraryEventHandlerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private Consumer<Integer, String> consumer;

    @Autowired
    private EmbeddedKafkaBroker broker;

    @Autowired
    private KafkaConfig config;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        Map<String, Object> map = new HashMap<>(KafkaTestUtils.consumerProps("group1", "true", broker));
        consumer = new DefaultKafkaConsumerFactory<>(map,
                new IntegerDeserializer(),
                new StringDeserializer()).createConsumer();
        broker.consumeFromAllEmbeddedTopics(consumer);
    }

    @AfterEach
    void tearDown() {
        consumer.close();
    }

    @Test
    void postEvent() {
        Book book = Book.builder()
                .id(123)
                .authorName("Andy")
                .name("Elder Ring I")
                .build();
        LibraryEvent event = LibraryEvent.builder()
                .eventId(null)
                .book(book)
                .build();

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        RequestEntity<LibraryEvent> requestEntity = new RequestEntity<>(
                event,
                headers,
                HttpMethod.POST,
                URI.create("/v1/libraryEvents"));

        ResponseEntity<LibraryEvent> exchange = restTemplate.exchange(requestEntity, LibraryEvent.class);

        assertEquals(HttpStatus.CREATED, exchange.getStatusCode());

        ConsumerRecord<Integer, String> record = KafkaTestUtils.getSingleRecord(consumer,
                config.getTopic(),
                5000);

        Header mySpecialKey = record.headers()
                .lastHeader("mySpecialKey");
        String value = new String(mySpecialKey.value(), Charset.defaultCharset());
        System.out.println(record.value());
        assertEquals("Hello", value);

    }

    @Test
    void postEventReactive() {
        Book book = Book.builder()
                .id(123)
                .authorName("Andy")
                .name("Elder Ring I")
                .build();
        LibraryEvent event = LibraryEvent.builder()
                .eventId(null)
                .book(book)
                .build();

        URI uri = UriComponentsBuilder.fromUriString("/v2/libraryEvents")
                .build()
                .toUri();
        webTestClient.post()
                .uri(uri)
                .bodyValue(event)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void putEventReactive() {
        Book book = Book.builder()
                .id(123)
                .authorName("Andy")
                .name("Elder Ring I")
                .build();
        LibraryEvent event = LibraryEvent.builder()
                .eventId(1)
                .book(book)
                .build();

        URI uri = UriComponentsBuilder.fromUriString("/v2/libraryEvents/${eventId}")
                .build(event.getEventId());
        webTestClient.put()
                .uri(uri)
                .bodyValue(event)
                .exchange()
                .expectStatus()
                .isOk();

        int partition = KafkaTestUtils.getSingleRecord(consumer, config.getTopic(), 5000)
                .partition();
        System.out.println("First call partition is " + partition);

        webTestClient.put()
                .uri(uri)
                .bodyValue(event)
                .exchange()
                .expectStatus()
                .isOk();

        int partition1 = KafkaTestUtils.getSingleRecord(consumer, config.getTopic(), 5000)
                .partition();
        System.out.println("Second call partition is " + partition1);

        assertEquals(partition, partition1);


    }
}
