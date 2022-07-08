package com.eazybytes.loans.controller;

import com.eazybytes.loans.client.AccountClient;
import com.eazybytes.loans.client.CardClient;
import com.eazybytes.loans.model.CustomerView;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final AccountClient accountClient;
    private final CardClient cardClient;

    public CustomerController(AccountClient accountClient, CardClient cardClient) {
        this.accountClient = accountClient;
        this.cardClient = cardClient;
    }

    @GetMapping("/{custId}")
    public Mono<CustomerView> getCustomerById(@RequestHeader("cloudbank-correlation-key") String key,  @PathVariable Integer custId) {
        return accountClient.getCustomerById(custId)
                .flatMap(customer -> cardClient.getCardDetails(key, custId)
                        .collectList()
                        .map(cards -> new CustomerView(customer, cards)))
                .log();

    }
}
