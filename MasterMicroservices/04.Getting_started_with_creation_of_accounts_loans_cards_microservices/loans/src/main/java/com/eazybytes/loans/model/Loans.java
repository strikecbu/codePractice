package com.eazybytes.loans.model;

import java.sql.Date;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter @Setter @ToString
@Table("loans")
public class Loans {

	@Id
	@Column("loan_number")
	private int loanNumber;
	
	@Column("customer_id")
	private int customerId;
	
	@Column("start_dt")
	private Date startDt;
	
	@Column("loan_type")
	private String loanType;
	
	@Column("total_loan")
	private int totalLoan;
	
	@Column("amount_paid")
	private int amountPaid;
	
	@Column("outstanding_amount")
	private int outstandingAmount;
	
	@Column("create_dt")
	private Date createDt;
	
}
