/**
 * 
 */
package com.eazybytes.accounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.eazybytes.accounts.model.Accounts;
import com.eazybytes.accounts.model.Customer;
import com.eazybytes.accounts.repository.AccountsRepository;

/**
 * @author Eazy Bytes
 *
 */

@RestController
@RequestMapping("accounts")
public class AccountsController {
	
	private final AccountsRepository accountsRepository;

	public AccountsController(AccountsRepository accountsRepository) {
		this.accountsRepository = accountsRepository;
	}

	@GetMapping("")
	public Accounts getAccountDetails(@RequestParam Integer custId) {
		return accountsRepository.findByCustomerId(custId);
	}

}
