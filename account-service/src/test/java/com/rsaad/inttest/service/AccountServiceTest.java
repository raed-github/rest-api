package com.rsaad.inttest.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rsaad.dto.AccountDto;
import com.rsaad.dto.CustomerDto;
import com.rsaad.dto.OpenAccountDto;
import com.rsaad.dto.TransactionDto;
import com.rsaad.feign.client.CustomerClient;
import com.rsaad.feign.client.TransactionClient;
import com.rsaad.model.Account;
import com.rsaad.model.Transaction;
import com.rsaad.repository.AccountRepository;
import com.rsaad.service.impl.AccountServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {	
	@InjectMocks
	private AccountServiceImpl accountService;
	@Mock
	private AccountRepository accountRepository;
	@Mock
	private CustomerClient customerClient;
	@Mock
	private TransactionClient transactionClient;
	
	@Test
	void givenOpenAccountDto_whenOpenAccount_ReturnAccountDto() {
		Account account = Account.builder()
				.id("XS34-5423-9880-4333")
				.customerId("342D-D43S-8390-487DH")
				.dateCreated("12/6/2022").build();
		OpenAccountDto openAccountDto = OpenAccountDto.builder().customerId("342D-D43S-8390-487DH").balance(2.0).build();
		
		CustomerDto customerDto = CustomerDto.builder()
				.customerId("342D-D43S-8390-487DH")
				.dateCreated("12/2/2022")
				.name("Caren")
				.surName("Donalds").build();

		TransactionDto transactionDto
		= TransactionDto.builder()
		.accountId("XS34-5423-9880-4333")
		.amount(2.0)
		.currency("USD").build();

		ResponseEntity resp = new ResponseEntity<CustomerDto>(customerDto,HttpStatus.OK);
		
		when(customerClient.findCustomerById(openAccountDto.getCustomerId())).thenReturn(resp);

		when(accountRepository.save(any(Account.class))).thenReturn(account);
		
		doNothing().when(transactionClient).createTransaction(transactionDto);
		
		transactionClient.createTransaction(transactionDto);

		AccountDto accountDto = accountService.openAccount(openAccountDto);
		
		assertTrue(accountDto.getAccountId().equals(account.getId()));
		assertTrue(accountDto.getBalanace().equals(openAccountDto.getBalance()));
		assertTrue(accountDto.getCustomerId().equals(openAccountDto.getCustomerId()));

	}
	


}
