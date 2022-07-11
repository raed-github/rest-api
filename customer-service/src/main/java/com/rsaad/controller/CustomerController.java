package com.rsaad.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rsaad.dto.CustomerDto;
import com.rsaad.dto.CustomerInfoDto;
import com.rsaad.exception.CustomerNotFoundException;
import com.rsaad.service.CustomerService;

@RestController
@RequestMapping("/v1")
@CrossOrigin()
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
    @GetMapping("/customers/customer-info/{id}")
    public ResponseEntity<CustomerInfoDto> customerInfo(@PathVariable("id") String id) {
		return customerService.getCustomerInfo(id)
				.map(ResponseEntity::ok)
				.orElseGet(()->{
					throw new CustomerNotFoundException();
				});
    }

    @PostMapping("/customers")
	public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
    	customerDto = customerService.createNewCustomer(customerDto);
    	return new ResponseEntity<>(customerDto, HttpStatus.CREATED);
	}
    
    @PutMapping("/customers/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("id") String id, @RequestBody CustomerDto customerDto) {
		return customerService.findCustomerById(id)
    			.map(savedCustomerDto->{
    				savedCustomerDto.setName(customerDto.getName());
    				savedCustomerDto.setSurName(customerDto.getSurName());
    				savedCustomerDto.setAccounts(customerDto.getAccounts());
    				
    				CustomerDto updatedCustomer = customerService.updateCustomer(savedCustomerDto);
    				return new ResponseEntity<>(updatedCustomer,HttpStatus.OK);
    			}).orElseGet(()->ResponseEntity.notFound().build());
    }
    
    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerDto> findCustomerById(@PathVariable("id") String id) {
		return customerService.findCustomerById(id)
				.map(ResponseEntity::ok)
				.orElseGet(()->{
					throw new CustomerNotFoundException();
				});
    }
    
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable String id) {
		customerService.deleteCustomer(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
}
