package com.rsaad.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rsaad.constants.AccountConstants;
import com.rsaad.dto.AccountDto;
import com.rsaad.dto.CustomerDto;
import com.rsaad.dto.OpenAccountDto;
import com.rsaad.dto.TransactionDto;
import com.rsaad.dto.mapper.DtoMapper;
import com.rsaad.feign.client.CustomerClient;
import com.rsaad.feign.client.TransactionClient;
import com.rsaad.model.Account;
import com.rsaad.model.Transaction;
import com.rsaad.repository.AccountRepository;
import com.rsaad.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private CustomerClient customerClient;
	@Autowired
	private TransactionClient transactionClient;

	@Override
	public AccountDto openAccount(OpenAccountDto openAccountDto) {
		
		ResponseEntity<CustomerDto> resp = customerClient.findCustomerById(openAccountDto.getCustomerId());
		CustomerDto customerDto = resp.getBody();
		
		Account account = Account.builder()
		.balanace(openAccountDto.getBalance())
		.customerId(openAccountDto.getCustomerId())
		.build();

		account = accountRepository.save(account);

		if(openAccountDto.getBalance() > 0.0) {
			TransactionDto transactionDto = TransactionDto.builder()
			.amount(openAccountDto.getBalance())
			.currency(AccountConstants.ACCOUNT_CURRENCY)
			.fromAccount(null)
			.toAccount(null)
			.transactionDate(null)
			.accountId(account.getId())
			.transactionType(AccountConstants.TRANSACTION_TYPE_DEPOSIT).build();
			
			account.setBalanace(openAccountDto.getBalance());
			account = accountRepository.save(account);
	        
			transactionClient.createTransaction(transactionDto);
		}	
		return DtoMapper.toAccountDto(account);
	}

	@Override
	public Optional<List<String>> findCustomerAccounts(String customerId) {
		List<Account> accounts = accountRepository.findAccountsByCustomerId(customerId);
		if(null != accounts && !accounts.isEmpty()) {
			List customerAccounts = accounts.stream().map(account->account.getId()).collect(Collectors.toList());
			return Optional.of(customerAccounts);
		}
		return Optional.empty();
	}

	@Override
	public AccountDto createNewAccount(AccountDto accountDto) {
		Account account = accountRepository.save(DtoMapper.toAccount(accountDto));
		return DtoMapper.toAccountDto(account);
	}

	@Override
	public AccountDto updateAccount(AccountDto accountDto) {
		Account account = accountRepository.save(DtoMapper.toAccount(accountDto));
		return DtoMapper.toAccountDto(account);
	}

	@Override
	public void deleteAccount(String id) {
		Account account = Account.builder().id(id).build();
		accountRepository.delete(account);		
	}

	@Override
	public Optional<AccountDto> findAccountById(String id) {
		Optional<Account> account = accountRepository.findById(id);
		if(account.isPresent()) {
			return Optional.of(DtoMapper.toAccountDto(account.get()));
		}
		return Optional.empty();
	}

	@Override
	public List<AccountDto> findAllAccounts() {
		List<Account> accounts = accountRepository.findAll();
		return accounts.stream()
				.map(DtoMapper::toAccountDto)
				.collect(Collectors.toList());
	}
	
	public AccountRepository getAccountRepository() {
		return accountRepository;
	}

	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	
}
