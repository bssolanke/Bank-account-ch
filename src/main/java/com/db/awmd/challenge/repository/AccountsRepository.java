package com.db.awmd.challenge.repository;

import java.math.BigDecimal;
import java.util.List;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.Transection;
import com.db.awmd.challenge.exception.AccountNotFoundException;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;

public interface AccountsRepository {

  void createAccount(Account account) throws DuplicateAccountIdException;

  Account getAccount(String accountId) throws AccountNotFoundException;
  
  String authenticate(String accountId,String password) throws AccountNotFoundException;
  
  String lockUnlockUser(String account) throws AccountNotFoundException;
  
  String deposit(String accountId,BigDecimal amount) throws AccountNotFoundException;
  
  String withdraw(String accountId,BigDecimal amount) throws AccountNotFoundException;
  
  List<Transection> searchTransectionbyAccount(String accountId) throws AccountNotFoundException;

  void clearAccounts();


}
