package guru.springframework.msscbrewery.web.mappers;

import guru.springframework.msscbrewery.domain.Customer;
import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerMapperTest {

    @Test
    void customerDtoToCustomer() {
        CustomerDto dto = CustomerDto.builder()
                .name("Andy")
                .updateTime(LocalDateTime.now())
                .build();
        Customer customer = CustomerMapper.INSTANCE.customerDtoToCustomer(dto);
        assertEquals(dto.getName(), customer.getName());
    }

    @Test
    void customerToCustomerDto() {

    }
}
