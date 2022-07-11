package com.rsaad.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.rsaad.dto.CustomerDto;
import com.rsaad.dto.CustomerInfoDto;

@FeignClient(name="customer-service", url="http://localhost:8091/v1")
public interface CustomerClient {

	@PostMapping("/customers")
	public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto);

	@PutMapping("/customers/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("id") String id, @RequestBody CustomerDto customerDto);

    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerDto> findCustomerById(@PathVariable("id") String id);

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable String id);
    
    @GetMapping("/customers/{id}/transactions")
    public ResponseEntity<CustomerInfoDto> customerInfo(@PathVariable("id") String id);
}
