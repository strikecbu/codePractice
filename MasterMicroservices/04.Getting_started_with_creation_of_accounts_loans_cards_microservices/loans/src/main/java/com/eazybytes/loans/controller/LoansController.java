/**
 * 
 */
package com.eazybytes.loans.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.eazybytes.loans.model.Customer;
import com.eazybytes.loans.model.Loans;
import com.eazybytes.loans.repository.LoansRepository;
import reactor.core.publisher.Flux;

/**
 * @author Eazy Bytes
 *
 */

@RestController
@RequestMapping("loans")
public class LoansController {

	private final LoansRepository loansRepository;

	public LoansController(LoansRepository loansRepository) {
		this.loansRepository = loansRepository;
	}

	@GetMapping()
	public Flux<Loans> getLoansDetails(@RequestParam Integer custId) {
		return loansRepository.findByCustomerIdOrderByStartDtDesc(custId);
	}

}
