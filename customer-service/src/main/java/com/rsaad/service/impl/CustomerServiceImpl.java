package com.rsaad.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rsaad.dto.CustomerDto;
import com.rsaad.dto.CustomerInfoDto;
import com.rsaad.dto.TransactionDto;
import com.rsaad.dto.mapper.DtoMapper;
import com.rsaad.feign.client.TransactionClient;
import com.rsaad.model.Customer;
import com.rsaad.repository.CustomerRepository;
import com.rsaad.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private TransactionClient transactionClient;
	
	@Override
	public Optional<CustomerInfoDto> getCustomerInfo(String customerId) {
		Optional<CustomerDto> cutstomerDto = this.findCustomerById(customerId);
		if(cutstomerDto.isPresent()) {
			ResponseEntity<List<TransactionDto>> response = transactionClient.findCustomerTransactions(customerId);
			List<TransactionDto> transactionsDto = response.getBody();			
			return Optional.of(CustomerInfoDto.builder()
			.customerId(customerId)
			.name(cutstomerDto.get().getName())
			.surName(cutstomerDto.get().getSurName())
			.transactions(transactionsDto).build());
		}
		return Optional.empty();
	}
	
	@Override
	public CustomerDto createNewCustomer(CustomerDto customerDto) {
		Customer customer = customerRepository.save(DtoMapper.toCustomer(customerDto));
		return DtoMapper.toCustomerDto(customer);
	}

	@Override
	public CustomerDto updateCustomer(CustomerDto customerDto) {
		Customer customer = customerRepository.save(DtoMapper.toCustomer(customerDto));
		return DtoMapper.toCustomerDto(customer);
	}

	@Override
	public Optional<CustomerDto> findCustomerById(String id) {
		Optional<Customer>  customer = customerRepository.findById(id);
		if(customer.isPresent()) {
			return Optional.of(DtoMapper.toCustomerDto(customer.get()));
		}
		return Optional.empty();
//		return Optional.of(CustomerDto.builder().customerId("1234").name("test").surName("surName").build());
	}

	@Override
	public void deleteCustomer(String id) {
		Customer customer = Customer.builder().id(id).build();
		customerRepository.delete(customer);		
	}

	@Override
	public List<CustomerDto> findAllCustomers() {
		List<Customer> customers = customerRepository.findAll();
		return customers.stream()
				.map(DtoMapper::toCustomerDto)
				.collect(Collectors.toList());
	}
	
	public CustomerRepository getCustomerRepository() {
		return customerRepository;
	}

	public void setCustomerRepository(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

}
