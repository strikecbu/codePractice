package com.eazybytes.loans.repository;

import com.eazybytes.loans.model.Loans;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface LoansRepository extends ReactiveCrudRepository<Loans, Long> {


    Flux<Loans> findByCustomerIdOrderByStartDtDesc(int customerId);

}
