package com.db.awmd.challenge;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.service.account.AccountsService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

//import static com.db.awmd.challenge.AccountsServiceTest.generateAccountId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class AccountsControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private AccountsService accountsService;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Before
  public void prepareMockMvc() {
    this.mockMvc = webAppContextSetup(this.webApplicationContext).build();

     //Reset the existing accounts before each test.
    accountsService.getAccountsRepository().clearAccounts();
  }
  
  @Autowired
  ObjectMapper objectMapper;
  
	
	@Test
	@Description("lockUnlockAccount test cases")
	public void lockUnlockAccount() throws Exception {
	  MvcResult mvcResult= this.mockMvc.perform(post("/v1/accounts/lockUnlockAccount").contentType(MediaType.APPLICATION_JSON)
	          .content("").param("accountId", "11")).andReturn();
	  
	  assertNotNull(mvcResult.getResponse());
	  String result=mvcResult.getResponse().getContentAsString();
	  assertNotNull(result);
	  assertEquals("User 11 has been unlock.".trim(), result.trim());
	}
	
	@Test
	@Description("authenticate test cases")
	public void authenticate() throws Exception {
	  MvcResult mvcResult= this.mockMvc.perform(post("/v1/accounts/authenticate").contentType(MediaType.APPLICATION_JSON)
	          .content("").param("accountId", "11").param("password", "Abc@123")).andReturn();
	  
	  assertNotNull(mvcResult.getResponse());
	  String result=mvcResult.getResponse().getContentAsString();
	  assertNotNull(result);
	  assertEquals("User 11 have successfully logged in.".trim(), result.trim());
	}
	
	@Test
	@Description("deposit test cases")
	public void deposit() throws Exception {
	  MvcResult mvcResult= this.mockMvc.perform(post("/v1/accounts/deposit").contentType(MediaType.APPLICATION_JSON)
	          .content("").param("accountId", "11").param("amount", "2000")).andReturn();
	  
	  assertNotNull(mvcResult.getResponse());
	  String result=mvcResult.getResponse().getContentAsString();
	  assertNotNull(result);
	  assertEquals("User 11 has been credited for Rs 2000".trim(), result.trim());
	}
	
	@Test
	@Description("withdraw test cases")
	public void withdraw() throws Exception {
	  MvcResult mvcResult= this.mockMvc.perform(post("/v1/accounts/withdraw").contentType(MediaType.APPLICATION_JSON)
	          .content("").param("accountId", "11").param("amount", "500")).andReturn();
	  
	  assertNotNull(mvcResult.getResponse());
	  String result=mvcResult.getResponse().getContentAsString();
	  assertNotNull(result);
	  assertEquals("User 11 has been debited for Rs 500".trim(), result.trim());
	}
	
	@Test
	@Description("searchTransection test cases")
	public void searchTransection() throws Exception {
	  MvcResult mvcResult= this.mockMvc.perform(post("/v1/accounts/searchTransection").contentType(MediaType.APPLICATION_JSON)
	          .content("").param("accountId", "11")).andReturn();
	  
	  assertNotNull(mvcResult.getResponse());
	  String result=mvcResult.getResponse().getContentAsString();
	  assertNotNull(result);
	  
	}
	
//	@Test
//	@Description("Not found error")
//	public void lockUnlockAccount404() throws Exception {
//	  this.mockMvc.perform(post("/v1/accounts/lockUnlockAccount").contentType(MediaType.APPLICATION_JSON)
//	          .content("").param("accountId", "11")).andExpect(MockMvcResultMatchers.status().isNotFound());
//	  
//	}
//	
//	@Test
//	@Description("Method not allowed")
//	public void lockUnlockAccount405() throws Exception {
//	  this.mockMvc.perform(post("/v1/accounts/lockUnlockAccount").contentType(MediaType.APPLICATION_JSON)
//	          .content("").param("accountId", "11")).andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
//	  
//	}
  
//
//  @Test
//  public void createAccount() throws Exception {
//    this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
//            .content("{\"accountId\":\"Id-123\",\"balance\":1000}")).andExpect(status().isCreated());
//
//    Account account = accountsService.getAccount("Id-123");
//    assertThat(account.getAccountId()).isEqualTo("Id-123");
//    assertThat(account.getBalance()).isEqualByComparingTo("1000");
//  }
//
//  @Test
//  public void createDuplicateAccount() throws Exception {
//    this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
//            .content("{\"accountId\":\"Id-123\",\"balance\":1000}")).andExpect(status().isCreated());
//
//    this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
//            .content("{\"accountId\":\"Id-123\",\"balance\":1000}")).andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public void createAccountNoAccountId() throws Exception {
//    this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
//            .content("{\"balance\":1000}")).andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public void createAccountNoBalance() throws Exception {
//    this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
//            .content("{\"accountId\":\"Id-123\"}")).andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public void createAccountNoBody() throws Exception {
//    this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public void createAccountNegativeBalance() throws Exception {
//    this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
//            .content("{\"accountId\":\"Id-123\",\"balance\":-1000}")).andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public void createAccountEmptyAccountId() throws Exception {
//    this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
//            .content("{\"accountId\":\"\",\"balance\":1000}")).andExpect(status().isBadRequest());
//  }
  
  
//	@Test
//	public void getAccount() throws Exception {
//	  Account account = new Account("11", "Abc@123", Boolean.FALSE, new BigDecimal(1000));
//	  this.mockMvc.perform(post("/v1/accounts/authenticate?accountId=11&password=Abc@123").contentType(MediaType.APPLICATION_JSON))
//	          .andExpect(content().string("User 11 have successfully logged in."));
//	}
  

//  @Test
//  public void getAccount() throws Exception {
//    String uniqueAccountId = "Id-" + System.currentTimeMillis();
//    Account account = new Account(uniqueAccountId,"Abc@123", new BigDecimal("123.45"));
//    this.accountsService.createAccount(account);
//    this.mockMvc.perform(get("/v1/accounts/" + uniqueAccountId))
//            .andExpect(status().isOk())
//            .andExpect(
//                    content().string("{\"accountId\":\"" + uniqueAccountId + "\",\"balance\":123.45}"));
//  }
//
//  @Test
//  public void transfer() throws Exception {
//    Account sender = new Account(generateAccountId(),"Abc@123", BigDecimal.TEN);
//    accountsService.createAccount(sender);
//    Account receiver = new Account(generateAccountId(),"Abc@123", BigDecimal.TEN);
//    accountsService.createAccount(receiver);
//
//    BigDecimal amount = BigDecimal.ONE;
//    mockMvc.perform(post("/v1/accounts/" + sender.getAccountId() + "/transfer")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content("{\"receiverId\":\"" + receiver.getAccountId() + "\", \"amount\":" + amount + "}"))
//            .andExpect(status().isOk());
//  }
//
//  @Test
//  public void transferNotEnoughBalance() throws Exception {
//    Account sender = new Account(generateAccountId(),"Abc@123",  BigDecimal.ONE);
//    accountsService.createAccount(sender);
//    Account receiver = new Account(generateAccountId(),"Abc@123", BigDecimal.TEN);
//    accountsService.createAccount(receiver);
//
//    BigDecimal amount = BigDecimal.TEN;
//    mockMvc.perform(post("/v1/accounts/" + sender.getAccountId() + "/transfer")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content("{\"receiverId\":\"" + receiver.getAccountId() + "\", \"amount\":" + amount + "}"))
//            .andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public void transferNegativeAmount() throws Exception {
//    Account sender = new Account(generateAccountId(), "Abc@123", BigDecimal.TEN);
//    accountsService.createAccount(sender);
//    Account receiver = new Account(generateAccountId(),"Abc@123", BigDecimal.TEN);
//    accountsService.createAccount(receiver);
//
//    BigDecimal amount = BigDecimal.TEN.negate();
//    mockMvc.perform(post("/v1/accounts/" + sender.getAccountId() + "/transfer")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content("{\"receiverId\":\"" + receiver.getAccountId() + "\", \"amount\":" + amount + "}"))
//            .andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public void transferReceiverNotFound() throws Exception {
//    Account sender = new Account(generateAccountId(), "Abc@123", BigDecimal.TEN);
//    accountsService.createAccount(sender);
//
//    BigDecimal amount = BigDecimal.ONE;
//    mockMvc.perform(post("/v1/accounts/" + sender.getAccountId() + "/transfer")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content("{\"receiverId\":\"" + generateAccountId() + "\", \"amount\":" + amount + "}"))
//            .andExpect(status().isNotFound());
//  }
//
//  @Test
//  public void transferSenderNotFound() throws Exception {
//    Account receiver = new Account(generateAccountId(), "Abc@123", BigDecimal.TEN);
//    accountsService.createAccount(receiver);
//
//    BigDecimal amount = BigDecimal.ONE;
//    mockMvc.perform(post("/v1/accounts/" + generateAccountId() + "/transfer")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content("{\"receiverId\":\"" + receiver.getAccountId() + "\", \"amount\":" + amount + "}"))
//            .andExpect(status().isNotFound());
 // }
}
