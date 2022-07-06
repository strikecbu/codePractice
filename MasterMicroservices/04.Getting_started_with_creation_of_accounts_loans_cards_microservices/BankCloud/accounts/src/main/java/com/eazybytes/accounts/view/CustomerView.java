package com.eazybytes.accounts.view;

import com.eazybytes.accounts.model.Cards;
import com.eazybytes.accounts.model.Customer;
import com.eazybytes.accounts.model.Loans;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CustomerView {

    private Customer customer;
    private List<Cards> cards;
    private List<Loans> loans;
}
