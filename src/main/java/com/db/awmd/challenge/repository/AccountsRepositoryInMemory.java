package com.db.awmd.challenge.repository;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.ApplicationConstant;
import com.db.awmd.challenge.domain.Transection;
import com.db.awmd.challenge.exception.AccountNotFoundException;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class AccountsRepositoryInMemory implements AccountsRepository {

	private final Map<String, Account> accounts = new ConcurrentHashMap<>();
	public static Map<String, Account> accountMap = new ConcurrentHashMap<String, Account>();
	public static List<Transection> transectionList = new ArrayList<Transection>();

	 static {

		accountMap.put("11",new Account("11", "Abc@123", Boolean.FALSE, new BigDecimal(1000)));
		accountMap.put("22", new Account("22", "Pqr@123", Boolean.FALSE, new BigDecimal(2000)));
		accountMap.put("33", new Account("33", "Efg@123", Boolean.FALSE, new BigDecimal(3000)));
		accountMap.put("44", new Account("44", "Ijk@123", Boolean.FALSE, new BigDecimal(4000)));
		accountMap.put("55", new Account("55", "Abc@123", Boolean.TRUE, new BigDecimal(5000)));

	}

	

	/*
	 * @Bharat Solanke- authenticate user(non-Javadoc) 14-2-2022
	 */
	@Override
	public String authenticate(String accountId,String password) throws AccountNotFoundException {
		Account userAccount = accountMap.get(accountId.trim());
		String response = new String();
		if (userAccount == null) {
			throw new AccountNotFoundException(accountId);
		} else if (userAccount.getAccountId().equals(accountId.trim())
				&& userAccount.getPassword().equals(password.trim())) {
			response = "User " + accountId + " have successfully logged in. ";
		}

		return response;
	}

	/*
	 * @Bharat Solanke- lock and unlock user 14-2-2022
	 */
	@Override
	public String lockUnlockUser(String accountId) throws AccountNotFoundException {
		Account userAccount = accountMap.get(accountId.trim());
		String response = new String();
		if (userAccount == null) {
			throw new AccountNotFoundException(accountId);
		} else if (userAccount.getAccountId().equals(accountId.trim())
				&& userAccount.getIsLock().equals(Boolean.TRUE)) {
			response = "User " + accountId + " has been lock. ";
			userAccount.setLock(Boolean.FALSE);
		} else if (userAccount.getAccountId().equals(accountId.trim())
				&& userAccount.getIsLock().equals(Boolean.FALSE)) {
			response = "User " + accountId + " has been unlock. ";
			userAccount.setLock(Boolean.TRUE);
		}

		return response;
	}

	/*
	 * @bharat Solanke
	 * deposit amount for balance of given accountId
	 * 15-2-2022
	 */
	@Override
	public synchronized String deposit(String accountId,BigDecimal amount) throws AccountNotFoundException {
		Account userAccount = accountMap.get(accountId.trim());
		String response = new String();
		if (userAccount == null) {
			throw new AccountNotFoundException(userAccount.getAccountId());
		} else {
			userAccount.getBalance().add(amount);
			response="User " + accountId + " has been credited for Rs "+amount;
			
			Transection transection=new Transection();
			transection.setAccountId(accountId);
			transection.setTransectionType(ApplicationConstant.CREDITTYPE);
			transection.setAmount(amount);
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");  
			
			transection.setTransectionDate(formatter.format(new Date()));
			
			transectionList.add(transection);
		}
		accountMap.put(userAccount.getAccountId(), userAccount);
		return response;
	}

	/*
	 * @bharat Solanke
	 * withdrwaw amount for balance of given accountId
	 *  * 15-2-2022
	 */
	@Override
	public synchronized String withdraw(String accountId,BigDecimal amount) throws AccountNotFoundException {
		Account userAccount = accountMap.get(accountId.trim());
		String response = new String();
		if (userAccount == null) {
			throw new AccountNotFoundException(userAccount.getAccountId());
		} else {
			userAccount.getBalance().subtract(amount);
			response="User " + accountId + " has been debited for Rs "+amount;
			
			Transection transection=new Transection();
			transection.setAccountId(accountId);
			transection.setTransectionType(ApplicationConstant.DEBITTYPE);
			transection.setAmount(amount);
			SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");  
			
			transection.setTransectionDate(formatter.format(new Date()));
			transectionList.add(transection);
			
		}
		accountMap.put(userAccount.getAccountId(), userAccount);
		return response;
	}
	/*
	 * @bharat Solanke
	 * search Transection by accountId
	 */
	@Override
	public List<Transection> searchTransectionbyAccount(String accountId) throws AccountNotFoundException {
		List<Transection> resultList=new ArrayList<>();
		
		for(Transection transection:transectionList) {
			if(transection.getAccountId().equalsIgnoreCase(accountId.trim())) {
				resultList.add(transection);
			}
		}
		
		return resultList;
	}
	

	@Override
	public void clearAccounts() {
		accounts.clear();
	}

	@Override
	public Account getAccount(String accountId) throws AccountNotFoundException {

		return null;
	}

	@Override
	public void createAccount(Account account) throws DuplicateAccountIdException {
		Account previousAccount = accounts.putIfAbsent(account.getAccountId(), account);
		if (previousAccount != null) {
			throw new DuplicateAccountIdException("Account id " + account.getAccountId() + " already exists!");
		}
	}

}
