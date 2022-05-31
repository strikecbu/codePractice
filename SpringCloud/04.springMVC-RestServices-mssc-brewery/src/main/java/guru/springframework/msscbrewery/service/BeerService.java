package guru.springframework.msscbrewery.service;

import guru.springframework.msscbrewery.web.model.BeerDto;

import java.util.UUID;

public interface BeerService {

    BeerDto getBeerById(UUID beerId);
}
