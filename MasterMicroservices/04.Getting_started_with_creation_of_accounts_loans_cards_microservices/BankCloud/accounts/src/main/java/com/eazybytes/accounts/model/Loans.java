package com.eazybytes.accounts.model;

import lombok.Data;

import java.sql.Date;

@Data
public class Loans {

    private int loanNumber;

    private int customerId;

    private Date startDt;

    private String loanType;

    private int totalLoan;

    private int amountPaid;

    private int outstandingAmount;

    private Date createDt;

}
