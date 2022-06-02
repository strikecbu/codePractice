package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
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
    private String apiHost;

    private final RestTemplate restTemplate;

    public BreweryClient( RestTemplateBuilder restTemplateBuilder) {
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
}
