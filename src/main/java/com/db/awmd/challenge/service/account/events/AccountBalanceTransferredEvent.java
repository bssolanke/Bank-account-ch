package com.db.awmd.challenge.service.account.events;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.event.GenericEvent;
import lombok.Data;

import java.math.BigDecimal;


public class AccountBalanceTransferredEvent extends GenericEvent<AccountBalanceTransferredEvent.Payload> {

  @Data
  public static class Payload {
    public Payload(Account sender2, Account receiver2, BigDecimal amount2) {
		this.sender=sender2;
		this.receiver=receiver2;
		this.amount=amount2;
	}
	private final Account sender;
    private final Account receiver;
    private final BigDecimal amount;
	public Account getSender() {
		return sender;
	}
	public Account getReceiver() {
		return receiver;
	}
	public BigDecimal getAmount() {
		return amount;
	}
    
    
  }

  public AccountBalanceTransferredEvent(Account sender, Account receiver, BigDecimal amount) {
    super(new Payload(sender, receiver, amount));
  }
}
