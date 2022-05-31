package guru.springframework.msscbrewery.services;

import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public CustomerDto getCustomerById(UUID custId) {
        return CustomerDto.builder()
                .id(UUID.randomUUID())
                .name("Andy")
                .build();
    }

    @Override
    public CustomerDto saveCustomer(CustomerDto customer) {
        return CustomerDto.builder()
                .id(UUID.randomUUID())
                .name(customer.getName())
                .build();
    }

    @Override
    public void updateCustomer(UUID customerId, CustomerDto customerDto) {
        // update
    }

    @Override
    public void deleteCustomer(UUID customerId) {

    }
}
