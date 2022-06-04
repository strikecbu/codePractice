package com.reactivespring.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.Optional;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<String> errorHandle(WebExchangeBindException ex) {
        String collect = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> {
                    Optional<String> optional = Optional.ofNullable(fieldError.getDefaultMessage());
                    return optional.orElse("");
                })
                .sorted()
                .collect(Collectors.joining(", "));
        return new ResponseEntity<>(collect, HttpStatus.BAD_REQUEST);

    }
}
