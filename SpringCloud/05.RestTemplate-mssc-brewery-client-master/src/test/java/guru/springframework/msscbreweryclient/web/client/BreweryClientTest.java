package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import guru.springframework.msscbreweryclient.web.model.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BreweryClientTest {

    @Autowired
    private BreweryClient breweryClient;

    @Test
    void getBeerById() {
        BeerDto beer = breweryClient.getBeerById(UUID.randomUUID());
        assertNotNull(beer);
    }

    @Test
    void postBeer() {
        BeerDto goodBeer = BeerDto.builder()
                .beerName("GoodBeer")
                .build();
        URI uri = breweryClient.saveBeer(goodBeer);
        assertNotNull(uri);
        System.out.println(uri);
    }

    @Test
    void updateBeer() {
        BeerDto goodBeer = BeerDto.builder()
                .beerName("GoodBeer")
                .build();
        breweryClient.updateBeer(UUID.randomUUID(), goodBeer);
    }

    @Test
    void deleteBeer() {
        breweryClient.deleteBeer(UUID.randomUUID());
    }

    @Test
    void getCustomerById() {
        CustomerDto beer = breweryClient.getCustomerById(UUID.randomUUID());
        assertNotNull(beer);
    }

    @Test
    void postCustomer() {
        CustomerDto goodCustomer = CustomerDto.builder()
                .name("GoodBeer")
                .build();
        URI uri = breweryClient.saveNewCustomer(goodCustomer);
        assertNotNull(uri);
        System.out.println(uri);
    }

    @Test
    void updateCustomer() {
        CustomerDto goodCustomer = CustomerDto.builder()
                .name("GoodBeer")
                .build();
        breweryClient.updateCustomer(UUID.randomUUID(), goodCustomer);
    }

    @Test
    void deleteCustomer() {
        breweryClient.deleteCustomer(UUID.randomUUID());
    }
}
