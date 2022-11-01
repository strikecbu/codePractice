package com.eazybytes.controller;

import com.eazybytes.model.Customer;
import com.eazybytes.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Customer customer) {
        Customer save = customerRepository.save(customer);
        if (save.getId() > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Register new customer success.");
        }
        return ResponseEntity.internalServerError().body("Something goes wrong.");
    }
}
