package com.eazybytes.accounts.view;

import com.eazybytes.accounts.model.Cards;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Customer {

    private com.eazybytes.accounts.model.Customer customer;
    private List<Cards> cards;
}
