/**
 * 
 */
package com.eazybytes.cards.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.eazybytes.cards.model.Cards;
import com.eazybytes.cards.model.Customer;
import com.eazybytes.cards.repository.CardsRepository;
import reactor.core.publisher.Flux;

/**
 * @author Eazy Bytes
 *
 */

@RestController
@RequestMapping("cards")
public class CardsController {

	private final CardsRepository cardsRepository;

	public CardsController(CardsRepository cardsRepository) {
		this.cardsRepository = cardsRepository;
	}

	@GetMapping()
	public Flux<Cards> getCardDetails(@RequestParam Integer custId) {
		return cardsRepository.findByCustomerId(custId);
	}

}
