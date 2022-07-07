package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.client.CardClient;
import com.eazybytes.accounts.client.LoanClient;
import com.eazybytes.accounts.model.Accounts;
import com.eazybytes.accounts.model.Cards;
import com.eazybytes.accounts.model.Customer;
import com.eazybytes.accounts.model.Loans;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.view.CustomerView;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("customers")
@Slf4j
public class CustomersController {

    private final CustomerRepository custRepository;
    private final AccountsRepository accountsRepository;

    private final CardClient cardClient;
    private final LoanClient loanClient;

    public CustomersController(CustomerRepository custRepository, CardClient cardClient, LoanClient loanClient,
                               AccountsRepository accountsRepository) {
        this.custRepository = custRepository;
        this.cardClient = cardClient;
        this.loanClient = loanClient;
        this.accountsRepository = accountsRepository;
    }

    @GetMapping("/{custId}")
//    @CircuitBreaker(name = "custDetailByCustId", fallbackMethod = "fallbackFindCustomerByIdWithoutCards")
    @Retry(name = "custDetailByCustId", fallbackMethod = "fallbackFindCustomerByIdWithoutCards")
    public ResponseEntity<CustomerView> findCustomerById(@RequestHeader("cloudbank-correlation-key") String key,
                                                         @PathVariable Integer custId) {

        Optional<Customer> optionalCustomer = custRepository.findById(custId);
        if (!optionalCustomer.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Accounts account = accountsRepository.findByCustomerId(custId);
        List<Cards> cardsList = cardClient.getCardDetails(key + "_a", custId);
        List<Loans> loans = loanClient.getLoansByCustId(custId);

        Customer customer = optionalCustomer.get();
        CustomerView build = CustomerView.builder()
                .customer(customer)
                .account(account)
                .cards(cardsList)
                .loans(loans)
                .build();

        return new ResponseEntity<>(build, HttpStatus.OK);
    }

    private ResponseEntity<CustomerView> fallbackFindCustomerByIdWithoutCards(@RequestHeader("cloudbank-correlation-key") String key,
                                                                              @PathVariable Integer custId,
                                                                              Throwable ex) {
        log.info("Fallback method execute while Exception: {}", ex.getMessage());
        Optional<Customer> optionalCustomer = custRepository.findById(custId);
        if (!optionalCustomer.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Accounts account = accountsRepository.findByCustomerId(custId);

        List<Loans> loans = loanClient.getLoansByCustId(custId);

        Customer customer = optionalCustomer.get();
        CustomerView build = CustomerView.builder()
                .customer(customer)
                .account(account)
                .loans(loans)
                .cards(null)
                .build();

        return new ResponseEntity<>(build, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<Customer> getCustomerById(@RequestParam Integer custId) {
        Optional<Customer> optionalCustomer = custRepository.findById(custId);
        return optionalCustomer.map(customer -> new ResponseEntity<>(customer, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/hello")
    @RateLimiter(name = "sayhello", fallbackMethod = "sayHelloFallback")
    public ResponseEntity<String> sayHello() {
        return new ResponseEntity<>("Hello, stay awhile and listen", HttpStatus.OK);
    }

    private ResponseEntity<String> sayHelloFallback(Throwable ex) {
        return new ResponseEntity<>("Hi, stay awhile and listen", HttpStatus.OK);
    }
}
