package com.db.awmd.challenge.domain;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class Transection {
	
	@NotNull
	@NotEmpty
	private String accountId;

	@NotNull
	@Min(value = 0, message = "Initial balance must be positive.")
	private BigDecimal amount;
	
	@NotNull
	@NotEmpty
	private String transectionType;
	
	@NotNull
	@NotEmpty
	private String transectionDate;
	
	

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	
	public String getTransectionType() {
		return transectionType;
	}

	public void setTransectionType(String transectionType) {
		this.transectionType = transectionType;
	}

	public String getTransectionDate() {
		return transectionDate;
	}

	public void setTransectionDate(String transectionDate) {
		this.transectionDate = transectionDate;
	}
	
	
	
	
	

}
