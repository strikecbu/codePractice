package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.client.CardClient;
import com.eazybytes.accounts.model.Cards;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.view.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("customers")
public class CustomersController {

    private final CustomerRepository custRepository;

    private final CardClient cardClient;

    public CustomersController(CustomerRepository custRepository, CardClient cardClient) {
        this.custRepository = custRepository;
        this.cardClient = cardClient;
    }

    @GetMapping("/{custId}")
    public ResponseEntity<Customer> findCustomerById(@PathVariable Integer custId) {
        Optional<com.eazybytes.accounts.model.Customer> optionalCustomer = custRepository.findById(custId);
        if (!optionalCustomer.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Cards> cardsList = cardClient.getCardDetails(custId);

        com.eazybytes.accounts.model.Customer customer = optionalCustomer.get();
        Customer build = Customer.builder()
                .customer(customer)
                .cards(cardsList)
                .build();

        return new ResponseEntity<>(build, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<com.eazybytes.accounts.model.Customer> getCustomerById(@RequestParam Integer custId){
        Optional<com.eazybytes.accounts.model.Customer> optionalCustomer = custRepository.findById(custId);
        return optionalCustomer.map(customer -> new ResponseEntity<>(customer, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
