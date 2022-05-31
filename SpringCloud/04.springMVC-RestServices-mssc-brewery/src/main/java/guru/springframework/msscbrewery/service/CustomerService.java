package guru.springframework.msscbrewery.service;

import guru.springframework.msscbrewery.web.model.Customer;

import java.util.UUID;

public interface CustomerService {
    Customer getCustomerById(UUID custId);
}
