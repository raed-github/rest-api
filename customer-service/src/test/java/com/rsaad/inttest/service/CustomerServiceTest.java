package com.rsaad.inttest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rsaad.dto.CustomerDto;
import com.rsaad.dto.CustomerInfoDto;
import com.rsaad.dto.TransactionDto;
import com.rsaad.feign.client.TransactionClient;
import com.rsaad.model.Customer;
import com.rsaad.repository.CustomerRepository;
import com.rsaad.service.impl.CustomerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
	
	@InjectMocks
	private CustomerServiceImpl customerService;
	
	@Mock
	private CustomerRepository customerRepostory;
	
	@Mock
	private TransactionClient transactionClient;
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	@DisplayName("customerService.getCustomerInfo(id) -> case customer exist")
	void givenCustomerId_WhenCreateCustomerExist_ThenReturnOptionalCustomerInfoDto() {
		TransactionDto transactionDto
		= TransactionDto.builder().accountId("bc84d8bf-37f1-b276-403f-4b16827feb2f").amount(2.0).currency("USD").build();
		
		List<TransactionDto> transactions = new ArrayList<>();
		transactions.add(transactionDto);
		
		ResponseEntity<List<TransactionDto>> responseEntity = new ResponseEntity<>(transactions,HttpStatus.OK);
		
		CustomerInfoDto customerInfo = CustomerInfoDto.builder()
				.customerId("bc84d4bf-37f1-403f-b276-4b16827feb2f")
				.name("chris")
				.surName("columbus")
				.transactions(transactions)
				.build();
		String id = "bc84d4bf-37f1-403f-b276-4b16827feb2f";
		
		Customer customer = Customer.builder()
				.id("bc84d4bf-37f1-403f-b276-4b16827feb2f")
				.dateCreated("12/2/2022")
				.name("Caren")
				.surName("Donalds").build();

		when(customerRepostory.findById(id)).thenReturn(Optional.of(customer));
		
		when(transactionClient.findCustomerTransactions(id)).thenReturn(responseEntity);

		Optional<CustomerInfoDto> custInfo = customerService.getCustomerInfo(id);
		
		assertTrue(custInfo.isPresent());
		assertTrue(custInfo.get().getCustomerId().equals(customer.getId()));
		assertTrue(custInfo.get().getName().equals(customer.getName()));
		assertTrue(custInfo.get().getSurName().equals(customer.getSurName()));
		assertTrue(custInfo.get().getTransactions().equals(transactions));
	}
	
	@Test
	@DisplayName("customerService.getCustomerInfo(id) -> case customer does not exist")
	void givenCustomerId_WhenCreateCustomerDoesNotExist_ThenReturnEmptyOptional() {
		String id = "bc84d4bf-37f1-403f-b276-4b16827feb2f";
		
		when(customerRepostory.findById(id)).thenReturn(Optional.empty());
		
		Optional<CustomerInfoDto> custInfo = customerService.getCustomerInfo(id);
		
		assertTrue(custInfo.isEmpty());
		
	}
   
	@Test
	void givenCustomerDto_WhenCreateCustomer_ThenReturnCustomerDto() {
		CustomerDto customerDto = CustomerDto.builder()
				.dateCreated("12/2/2022")
				.name("Caren")
				.surName("Donalds").build();

		Customer customer = Customer.builder()
				.id("bc84d4bf-37f1-403f-b276-4b16827feb2f")
				.dateCreated("12/2/2022")
				.name("Caren")
				.surName("Donalds").build();
		
		when(customerRepostory.save(any(Customer.class))).thenReturn(customer);
				
		customerDto = customerService.createNewCustomer(customerDto);
		
		assertEquals(customerDto.getCustomerId(),customer.getId());
		assertEquals(customerDto.getDateCreated(),customer.getDateCreated());
		assertEquals(customerDto.getName(),customer.getName());
		assertEquals(customerDto.getSurName(),customer.getSurName());
		assertEquals(customerDto.getDateUpdated(),customerDto.getDateUpdated());
	}
	
	@Test
	void givenCustomerDto_WhenFindCustomerById_ThenReturnCustomerDto() {
		Customer customer = Customer.builder()
				.id("bc84d4bf-37f1-403f-b276-4b16827feb2f")
				.dateCreated("12/2/2022")
				.name("Caren")
				.surName("Donalds").build();

		Optional<Customer> optionalOfCustomer = Optional.of(customer);

		String id = "bc84d4bf-37f1-403f-b276-4b16827feb2f";
		
		when(customerRepostory.findById(id)).thenReturn(optionalOfCustomer);

		Optional<CustomerDto> result = customerService.findCustomerById(id);
		
		assertTrue(result.isPresent());
		assertEquals(result.get().getCustomerId(),customer.getId());
		assertEquals(result.get().getDateCreated(),customer.getDateCreated());
		assertEquals(result.get().getDateUpdated(),customer.getDateUpdated());
		assertEquals(result.get().getName(),customer.getName());
		assertEquals(result.get().getSurName(),customer.getSurName());
	}
	
	@Test
	void givenCustomerDto_WhenFindCustomerById_ReturnOptionalOfEmpty() {
		String id = "bc84d4bf-37f1-403f-b276-4b16827feb2f";
		when(customerRepostory.findById(id)).thenReturn(Optional.empty());
		Optional<CustomerDto> result = customerService.findCustomerById(id);
		assertFalse(result.isPresent());
	}

}
