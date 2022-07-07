package com.eazybytes.accounts.client;

import com.eazybytes.accounts.model.Cards;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient("cards")
public interface CardClient {

    @RequestMapping(method = RequestMethod.GET, value = "/cards", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<Cards> getCardDetails(@RequestHeader("cloudbank-correlation-key") String key, @RequestParam Integer custId);

}
