package com.eazybytes.loans.client;

import com.eazybytes.loans.model.Cards;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;

//@Headers({ "Accept: application/json" })
@ReactiveFeignClient("cards")
public interface CardClient {

    //    @RequestLine("GET /cards")
    @RequestMapping(method = RequestMethod.GET, value = "/cards", consumes = MediaType.APPLICATION_JSON_VALUE)
    Flux<Cards> getCardDetails(@RequestHeader("cloudbank-correlation-key") String key, @RequestParam Integer custId);
}
