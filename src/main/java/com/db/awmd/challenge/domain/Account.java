package com.db.awmd.challenge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class Account {

  @NotNull
  @NotEmpty
  private final String accountId;
  
  @NotNull
  @NotEmpty
  private final String password;
  
  @NotNull
  @NotEmpty
  private  Boolean isLock;

  @NotNull
  @Min(value = 0, message = "Initial balance must be positive.")
  private BigDecimal balance;

//  public Account(String accountId,String password,
//		    @JsonProperty("balance") BigDecimal balance) {
//    this.accountId = accountId;
//    this.password=password;
//    this.balance = BigDecimal.ZERO;
//  }

  @JsonCreator
  public Account(@JsonProperty("accountId") String accountId,
    @JsonProperty("password") String password,
    @JsonProperty("isLock") Boolean isLock,
    @JsonProperty("balance") BigDecimal balance) {
    this.accountId = accountId;
    this.balance = balance;
    this.password=password;
    this.isLock=isLock;
  }
  

	public Boolean getIsLock() {
		return isLock;
	}
	
	public void setLock(Boolean lock) {
		 this.isLock=lock;
	}

	public String getPassword() {
		return password;
	}
	
	public  BigDecimal getBalance() {
		return balance;
	}
	
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	public String getAccountId() {
		return accountId;
	}
  
}
