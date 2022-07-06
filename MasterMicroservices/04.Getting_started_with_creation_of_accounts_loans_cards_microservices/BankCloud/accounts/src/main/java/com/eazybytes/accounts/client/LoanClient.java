package com.eazybytes.accounts.client;

import com.eazybytes.accounts.model.Loans;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("loans")
public interface LoanClient {

    @RequestMapping(value = "/loans", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE})
    List<Loans> getLoansByCustId(@RequestParam Integer custId);
}
