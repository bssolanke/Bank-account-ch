package com.db.awmd.challenge.domain;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationConstant {
	public static Map<String, Account> accountMap = new ConcurrentHashMap<String, Account>();
	
	public static final String CREDITTYPE="deposit";
	
	public static final String DEBITTYPE="withdraw";
	
	public static Map<String, Account> getAccountList() {
		
		accountMap.put("1", new Account("1","Abc@123",Boolean.FALSE, new BigDecimal(1000)));
		accountMap.put("2", new Account("2","Pqr@123",Boolean.FALSE, new BigDecimal(2000)));
		accountMap.put("3", new Account("3","Efg@123",Boolean.FALSE, new BigDecimal(3000)));
		accountMap.put("4", new Account("4","Ijk@123",Boolean.FALSE, new BigDecimal(4000)));
		accountMap.put("5", new Account("5","Abc@123",Boolean.TRUE, new BigDecimal(5000)));
		
		return accountMap;
		
	}
	
}
