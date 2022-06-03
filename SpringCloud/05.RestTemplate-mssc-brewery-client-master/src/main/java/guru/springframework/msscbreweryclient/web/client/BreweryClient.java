package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import guru.springframework.msscbreweryclient.web.model.CustomerDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Component
@ConfigurationProperties(value = "sfg.brewery", ignoreUnknownFields = false)
public class BreweryClient {
    private final String BEER_API_PATH = "/api/v1/beer";
    private final String CUSTOMER_PATH_V1 = "/api/v1/customer";
    private final RestTemplate restTemplate;
    private String apiHost;

    public BreweryClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public BeerDto getBeerById(UUID id) {
        return restTemplate.getForObject(apiHost + BEER_API_PATH + "/" + id, BeerDto.class);
    }

    public URI saveBeer(BeerDto beerDto) {
        return restTemplate.postForLocation(apiHost + BEER_API_PATH, beerDto);
    }

    public void updateBeer(UUID beerId, BeerDto beerDto) {
        restTemplate.put(apiHost + BEER_API_PATH + "/" + beerId, beerDto);
    }

    public void deleteBeer(UUID id) {
        restTemplate.delete(apiHost + BEER_API_PATH + "/" + id);
    }


    public void setApiHost(String apiHost) {
        this.apiHost = apiHost;
    }

    public CustomerDto getCustomerById(UUID customerId) {
        return restTemplate.getForObject(apiHost + CUSTOMER_PATH_V1 + "/" + customerId.toString(), CustomerDto.class);
    }

    public URI saveNewCustomer(CustomerDto customerDto) {
        return restTemplate.postForLocation(apiHost + CUSTOMER_PATH_V1, customerDto);
    }

    public void updateCustomer(UUID customerId, CustomerDto customerDto) {
        restTemplate.put(apiHost + CUSTOMER_PATH_V1 + "/" + customerId, customerDto);
    }

    public void deleteCustomer(UUID customerId) {
        restTemplate.delete(apiHost + CUSTOMER_PATH_V1 + "/" + customerId);
    }
}
