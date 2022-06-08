package com.reactivespring.errorhandler;

import com.reactivespring.exception.MoviesInfoClientException;
import com.reactivespring.exception.ReviewsClientException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class ClientErrorHandler {

    @ExceptionHandler(MoviesInfoClientException.class)
    public Mono<ResponseEntity<String>> movieInfoNotFound(MoviesInfoClientException ex) {
        if (HttpStatus.NOT_FOUND.value() == ex.getStatusCode()) {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()));
        }
        return Mono.just(ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage()));
    }
    @ExceptionHandler(ReviewsClientException.class)
    public Mono<ResponseEntity<String>> reviewNotFound(ReviewsClientException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()));
    }
    @ExceptionHandler(RuntimeException.class)
    public Mono<ResponseEntity<String>> runtimeEx(RuntimeException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage()));
    }
}
