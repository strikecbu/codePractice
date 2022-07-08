package com.eazybytes.cards.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LogService {

    public void logSome(String message) {
        log.info(message);
    }
}
