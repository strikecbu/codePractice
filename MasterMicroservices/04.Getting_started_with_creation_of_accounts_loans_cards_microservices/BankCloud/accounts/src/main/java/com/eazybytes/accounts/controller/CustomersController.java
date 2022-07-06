package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.client.CardClient;
import com.eazybytes.accounts.client.LoanClient;
import com.eazybytes.accounts.model.Cards;
import com.eazybytes.accounts.model.Customer;
import com.eazybytes.accounts.model.Loans;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.view.CustomerView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("customers")
public class CustomersController {

    private final CustomerRepository custRepository;

    private final CardClient cardClient;
    private final LoanClient loanClient;

    public CustomersController(CustomerRepository custRepository, CardClient cardClient, LoanClient loanClient) {
        this.custRepository = custRepository;
        this.cardClient = cardClient;
        this.loanClient = loanClient;
    }

    @GetMapping("/{custId}")
    public ResponseEntity<CustomerView> findCustomerById(@PathVariable Integer custId) {
        Optional<com.eazybytes.accounts.model.Customer> optionalCustomer = custRepository.findById(custId);
        if (!optionalCustomer.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Cards> cardsList = cardClient.getCardDetails(custId);
        List<Loans> loans = loanClient.getLoansByCustId(custId);

        Customer customer = optionalCustomer.get();
        CustomerView build = CustomerView.builder()
                .customer(customer)
                .cards(cardsList)
                .loans(loans)
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
