/**
 *
 */
package com.eazybytes.cards.controller;

import com.eazybytes.cards.config.ConfigProperties;
import com.eazybytes.cards.model.Cards;
import com.eazybytes.cards.repository.CardsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Properties;

/**
 * @author Eazy Bytes
 *
 */

@RestController
@RequestMapping("cards")
@Slf4j
public class CardsController {

    private final CardsRepository cardsRepository;

    private final ConfigProperties configProperties;

    public CardsController(CardsRepository cardsRepository, ConfigProperties configProperties) {
        this.cardsRepository = cardsRepository;
        this.configProperties = configProperties;
    }

    @GetMapping()
    public Flux<Cards> getCardDetails(@RequestHeader("cloudbank-correlation-key") String key,
                                      @RequestParam Integer custId) {
        log.info("correlation-key: {}", key);
        return cardsRepository.findByCustomerId(custId)
                .log();
    }

    @GetMapping("/properties")
    public Mono<Properties> getConfig() {
        Properties properties = new Properties();
        properties.put("msg", configProperties.getMsg());
        properties.put("buildVersion", configProperties.getBuildVersion());
        properties.put("mailDetails", configProperties.getMailDetails());
        properties.put("activeBranches", configProperties.getActiveBranches());
        return Mono.just(properties);
    }

}
