package com.eazybytes.cards.model;

import java.sql.Date;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@ToString
@Table("cards")
public class Cards {

	@Id
	@Column("card_id")
	private int cardId;

	@Column("customer_id")
	private int customerId;

	@Column("card_number")
	private String cardNumber;

	@Column("card_type")
	private String cardType;

	@Column("total_limit")
	private int totalLimit;

	@Column("amount_used")
	private int amountUsed;

	@Column("available_amount")
	private int availableAmount;

	@Column("create_dt")
	private Date createDt;

}
