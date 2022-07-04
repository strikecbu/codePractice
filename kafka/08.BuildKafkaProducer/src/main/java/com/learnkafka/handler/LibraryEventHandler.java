package com.learnkafka.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learnkafka.domain.LibraryEvent;
import com.learnkafka.producer.LibraryEventProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.net.URI;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Component
@Slf4j
public class LibraryEventHandler extends ValidatorHandler<LibraryEvent> {


    private final KafkaSender<Integer, String> kafkaSender;

    private final LibraryEventProducer producer;

    private final Validator validator;

    public LibraryEventHandler(KafkaSender<Integer, String> kafkaSender, LibraryEventProducer producer,
                               Validator validator) {
        super(validator);
        this.kafkaSender = kafkaSender;
        this.producer = producer;
        this.validator = validator;
    }

    public Mono<ServerResponse> postEvent(ServerRequest request) {
        return request.bodyToMono(LibraryEvent.class)
                .doOnNext(this::validRequest)
                //TODO invoke kafka
                .flatMap(event -> {
                    try {
                        log.info("Ready to send message.");
//                        producer.sendMessageWithFuture(event);
                        SendResult<Integer, String> sendResult = producer.sendMessageWithRecordSync(event);
                        RecordMetadata metadata = sendResult.getRecordMetadata();
                        log.info("Send success, key: {}, value: {}, topic: {}",
                                event.getEventId(),
                                sendResult.getProducerRecord()
                                        .value(),
                                metadata.topic());
                        log.info("Send message completed.");
                    } catch (JsonProcessingException | ExecutionException | InterruptedException | TimeoutException e) {
                        return Mono.error(new RuntimeException(e));
                    }
                    return ServerResponse.created(URI.create("/libraryEvents"))
                            .bodyValue(event);
                })
                .switchIfEmpty(ServerResponse.badRequest()
                        .bodyValue("Not provide body"))
                .onErrorResume(ResponseStatusException.class,
                        e -> ServerResponse.badRequest()
                                .bodyValue(Objects.requireNonNull(e.getReason())));
    }

    public Mono<ServerResponse> putEventReactive(ServerRequest request) {
        //TODO validate path variable

        Mono<SenderRecord<Integer, String, LibraryEvent>> map = request.bodyToMono(LibraryEvent.class)
                .doOnNext(this::validRequest)
                .doOnNext(event -> {
                    if (event.getEventId() == null) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event id is null");
                    }
                })
                .map(event -> {
                    try {
                        return producer.mapperSenderRecord(event);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .log();

        return kafkaSender.send(map)
                .single()
                .doOnNext(senderResult -> {
                    RecordMetadata metadata = senderResult.recordMetadata();
                    log.info("send success, key: {}, value: {}, topic: {}, partition: {}",
                            senderResult.correlationMetadata()
                                    .getEventId(),
                            senderResult.correlationMetadata(),
                            metadata.topic(),
                            metadata.partition());
                })
                .flatMap(senderResult -> ServerResponse.ok()
                        .bodyValue(senderResult.correlationMetadata()))
                .onErrorResume(ResponseStatusException.class,
                        e -> ServerResponse.badRequest()
                                .bodyValue(Objects.requireNonNull(e.getReason())));
    }

    public Mono<ServerResponse> postEventReactive(ServerRequest request) {

        Mono<SenderRecord<Integer, String, LibraryEvent>> map = request.bodyToMono(LibraryEvent.class)
                .doOnNext(this::validRequest)
                .map(event -> {
                    try {
                        return producer.mapperSenderRecord(event);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .log();

        return kafkaSender.send(map)
                .single()
                .doOnNext(senderResult -> {
                    RecordMetadata metadata = senderResult.recordMetadata();
                    log.info("send success, key: {}, value: {}, topic: {}",
                            senderResult.correlationMetadata()
                                    .getEventId(),
                            senderResult.correlationMetadata(),
                            metadata.topic());
                })
                .flatMap(senderResult -> ServerResponse.created(URI.create("/libraryEvents"))
                        .bodyValue(senderResult.correlationMetadata()))
                .onErrorResume(ResponseStatusException.class,
                        e -> ServerResponse.badRequest()
                                .bodyValue(Objects.requireNonNull(e.getReason())));

    }
}
