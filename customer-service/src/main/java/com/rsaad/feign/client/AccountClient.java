package com.rsaad.feign.client;

import java.util.List;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.rsaad.dto.AccountDto;
import com.rsaad.dto.OpenAccountDto;

import org.springframework.http.HttpStatus;

@FeignClient(name="account-service",url = "http://localhost:8090/v1")
public interface AccountClient {
	
    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> openAccount(@Valid @RequestBody OpenAccountDto openAccountDto);
    
    @GetMapping("/accounts/customer-accounts/{id}")    
    public ResponseEntity<List<String>> findCustomerAccounts(@PathVariable("id") String id);

    @PostMapping("/accounts1")
    @ResponseStatus(HttpStatus.CREATED)
	public void createAccount(@RequestBody AccountDto accountDto);
    
    @PutMapping("/accounts/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable("id") String id, @RequestBody AccountDto accountDto);
    
    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountDto> findById(@PathVariable("id") String id);
    
    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable String id);

}
