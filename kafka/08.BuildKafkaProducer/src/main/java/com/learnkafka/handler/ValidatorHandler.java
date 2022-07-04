package com.learnkafka.handler;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

public abstract class ValidatorHandler<T> {

    private final Validator validator;

    protected ValidatorHandler(Validator validator) {
        assert validator != null;
        this.validator = validator;
    }

    public void validRequest(T event) {
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(event,
                event.getClass()
                        .getName());
        validator.validate(event, errors);
        String errorStrings = errors.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        if (errors.getAllErrors().size() > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorStrings);
        }
    }
}
