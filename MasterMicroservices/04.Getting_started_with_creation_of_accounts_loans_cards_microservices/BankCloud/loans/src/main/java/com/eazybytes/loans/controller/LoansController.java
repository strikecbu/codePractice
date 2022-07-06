/**
 * 
 */
package com.eazybytes.loans.controller;

import com.eazybytes.loans.config.LoansServiceConfig;
import org.springframework.web.bind.annotation.*;

import com.eazybytes.loans.model.Loans;
import com.eazybytes.loans.repository.LoansRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Properties;

/**
 * @author Eazy Bytes
 *
 */

@RestController
@RequestMapping("loans")
public class LoansController {

	private final LoansRepository loansRepository;

	private final LoansServiceConfig config;

	public LoansController(LoansRepository loansRepository, LoansServiceConfig config) {
		this.loansRepository = loansRepository;
		this.config = config;
	}

	@GetMapping()
	public Flux<Loans> getLoansDetails(@RequestParam Integer custId) {
		return loansRepository.findByCustomerIdOrderByStartDtDesc(custId).log();
	}

	@GetMapping("/properties")
	public  Mono<Properties> getConfig() {
		Properties properties = new Properties();
		properties.put("msg", config.getMsg());
		properties.put("buildVersion", config.getBuildVersion());
		properties.put("mailDetails", config.getMailDetails());
		properties.put("activeBranches", config.getActiveBranches());
		return Mono.just(properties);
	}

}
