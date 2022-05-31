package guru.springframework.msscbrewery.services;

import guru.springframework.msscbrewery.web.model.Customer;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public Customer getCustomerById(UUID custId) {
        return Customer.builder()
                .id(UUID.randomUUID())
                .name("Andy")
                .build();
    }
}
