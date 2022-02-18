package com.db.awmd.challenge.web;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.Transection;
import com.db.awmd.challenge.exception.AccountNotFoundException;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.service.account.AccountsService;
import com.db.awmd.challenge.web.dto.AccountTransferBalanceRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
//@Slf4j
public class AccountsController {

  private final AccountsService accountsService;
  //Logger logger = LoggerFactory.getLogger(AccountsController.class);
  //private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);
  @Autowired
  public AccountsController(AccountsService accountsService) {
    this.accountsService = accountsService;
  }
  
  /* @Bharat
   * authenticate user
   * 
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping("/authenticate")
  public ResponseEntity<Object> authenticate(@RequestBody @Valid @RequestParam String accountId,
		  @RequestParam String password) {
    System.out.println("authenticate account {}"+ accountId);
    String response=new String();
    try {
    	response= this.accountsService.authenticate(accountId,password);
    } catch (AccountNotFoundException exception) {
      return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(response,HttpStatus.OK);
  }
  
  /* @Bharat
   * authenticate user
   * 
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping("/lockUnlockAccount")
  public ResponseEntity<Object> lockUnlockAccount(@RequestBody @Valid @RequestParam("accountId") String accountId) {
    System.out.println("lockUnlockAccount account {}"+ accountId);
    String response=new String();
    try {
    	response= this.accountsService.lockUnlockUser(accountId);
    } catch (AccountNotFoundException exception) {
      return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(response,HttpStatus.OK);
  }
  
  /* @Bharat
   * authenticate user
   * 
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping("/deposit")
  public ResponseEntity<Object> deposit(@RequestBody @Valid @RequestParam String accountId,@RequestParam BigDecimal amount) {
    System.out.println("lockUnlockAccount account {}"+ accountId);
    String response=new String();
    try {
    	response= this.accountsService.deposit(accountId, amount);
    } catch (AccountNotFoundException exception) {
      return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(response,HttpStatus.OK);
  }
  
  /* @Bharat
   * authenticate user
   * 
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping("/withdraw")
  public ResponseEntity<Object> withdraw(@RequestBody @Valid @RequestParam String accountId,@RequestParam BigDecimal amount) {
    System.out.println("withdraw account {}"+ accountId);
    String response=new String();
    try {
    	response= this.accountsService.withdraw(accountId, amount);
    } catch (AccountNotFoundException exception) {
      return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(response,HttpStatus.OK);
  }
  
  /* @Bharat
   * authenticate user
   * 
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping("/searchTransection")
  public ResponseEntity<Object> searchTransection(@RequestBody @Valid @RequestParam String accountId) {
   System.out.println("searchTransection account {}"+ accountId);
   List<Transection> transectionList=new ArrayList<Transection>();
    try {
    	transectionList= this.accountsService.searchTransectionbyAccount(accountId);
    } catch (AccountNotFoundException exception) {
      return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(transectionList,HttpStatus.OK);
  }
  
  
  
  /*
   * end
   */
  

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> createAccount(@RequestBody @Valid Account account) {
    System.out.println("Creating account {}"+ account);

    try {
      this.accountsService.createAccount(account);
    } catch (DuplicateAccountIdException daie) {
      return new ResponseEntity<>(daie.getMessage(), HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping(path = "/{accountId}")
  public Account getAccount(@PathVariable String accountId) {
	  System.out.println("Retrieving account for id {}"+ accountId);
    return this.accountsService.getAccount(accountId);
  }

  @Data
  public static class TransferRequest {
    @NotNull
    private String receiverId;
    @NotNull
    @Min(value = 0, message = "Transfer amount must be positive.")
    private BigDecimal amount;
  }

  @PostMapping(value = "/{accountId}/transfer", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> transferBalance(@PathVariable String accountId,
                                           @RequestBody @Valid AccountTransferBalanceRequest request) {

	//sSystem.out.println("Transferring funds {} from account #{} to account #{}"+ request.getAmount(), accountId, request.getReceiverId());
    
	Exception error = null;
    HttpStatus status = HttpStatus.OK;
    try {
      this.accountsService.transfer(accountId, request.getReceiverId(), request.getAmount());
    } catch (AccountNotFoundException ex) {
      error = ex;
      status = HttpStatus.NOT_FOUND;
    } catch (Exception ex) {
      error = ex;
      status = HttpStatus.BAD_REQUEST;
    }

    return error == null
            ? new ResponseEntity<>(status)
            : new ResponseEntity<>(error.getMessage(), status);
  }
}
