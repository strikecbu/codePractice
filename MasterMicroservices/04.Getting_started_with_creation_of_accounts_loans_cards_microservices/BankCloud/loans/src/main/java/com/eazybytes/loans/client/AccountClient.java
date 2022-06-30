package com.eazybytes.loans.client;

import com.eazybytes.loans.model.Customer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient("accounts")
public interface AccountClient {

    @RequestMapping(method = RequestMethod.GET, value = "/customers", consumes = MediaType.APPLICATION_JSON_VALUE)
    Mono<Customer> getCustomerById(@RequestParam Integer custId);
}
