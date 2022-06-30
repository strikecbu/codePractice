package com.eazybytes.loans.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Customer {

    private Integer customerId;
    private String name;
    private String email;
    private String mobileNumber;
    private LocalDate createDt;

}
