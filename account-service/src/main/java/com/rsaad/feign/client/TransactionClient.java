package com.rsaad.feign.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.rsaad.dto.TransactionDto;

@FeignClient(name="transaction-service",url="http://localhost:8092/v1")
public interface TransactionClient {
		
    @GetMapping("/transactions/{id}")
    public ResponseEntity<TransactionDto> findTransactionById(@PathVariable("id") String id);

    @PostMapping("/transactions")
    @ResponseStatus(HttpStatus.CREATED)
	public void createTransaction(@RequestBody TransactionDto transactionDto);

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable String id);
    
    @PutMapping("/transactions/{id}")
    public ResponseEntity<TransactionDto> updateTransaction(@PathVariable("id") String id, @RequestBody TransactionDto transactionDto);
   
    @GetMapping("/transactions/customer-transactions/{customerId}")
    public ResponseEntity<List<TransactionDto>> findCustomerTransactions(@PathVariable("customerId") String customeerId);

}
