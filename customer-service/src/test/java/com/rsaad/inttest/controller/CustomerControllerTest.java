package com.rsaad.inttest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsaad.controller.CustomerController;
import com.rsaad.dto.CustomerDto;
import com.rsaad.dto.CustomerInfoDto;
import com.rsaad.dto.TransactionDto;
import com.rsaad.service.CustomerService;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {	
	@MockBean
	private CustomerService customerService;
	@Autowired
	private MockMvc mockMvc;	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	@DisplayName("case customer has transactions")
	void givenCustomerDto_WhenCallCustomerInfo_ThenReturnCustomerInfo() throws JsonProcessingException, Exception {
		TransactionDto transactionDto
		= TransactionDto.builder().accountId("bc84d8bf-37f1-b276-403f-4b16827feb2f").amount(2.0).currency("USD").build();
		List<TransactionDto> transactions = new ArrayList<>();
		transactions.add(transactionDto);
		CustomerInfoDto customerInfo = CustomerInfoDto.builder()
				.customerId("bc84d4bf-37f1-403f-b276-4b16827feb2f")
				.name("chris")
				.surName("columbus")
				.transactions(transactions)
				.build();
		String id = "bc84d4bf-37f1-403f-b276-4b16827feb2f";
		
		when(customerService.getCustomerInfo(id)).thenReturn(Optional.of(customerInfo));

		mockMvc.perform(get("/v1/customers/customer-info/{id}",id).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(customerInfo)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.customerId").value(id))
		.andExpect(jsonPath("$.name").exists())
		.andExpect(jsonPath("$.surName").exists())
		.andExpect(jsonPath("$.transactions").exists())
		.andDo(print());		
		verify(customerService).getCustomerInfo(id);
	}
	
	@Test
	@DisplayName("case customer does not exist")
	void givenCustomerDto_WhenCallCustomerInfo_ThenReturnCustomerInfoWithoutTransactions() throws JsonProcessingException, Exception {
		TransactionDto transactionDto
		= TransactionDto.builder().accountId("bc84d8bf-37f1-b276-403f-4b16827feb2f").amount(2.0).currency("USD").build();
		List<TransactionDto> transactions = new ArrayList<>();
		transactions.add(transactionDto);
		CustomerInfoDto customerInfo = CustomerInfoDto.builder()
				.customerId("bc84d4bf-37f1-403f-b276-4b16827feb2f")
				.name("chris")
				.surName("columbus")
				.transactions(transactions)
				.build();
		String id = "bc84d4bf-37f1";
		
		when(customerService.getCustomerInfo(id)).thenReturn(Optional.empty());

		mockMvc.perform(get("/v1/customers/customer-info/{id}",id).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(customerInfo)))
		.andExpect(status().isNotFound())
		.andDo(print());		
		verify(customerService).getCustomerInfo(id);
	}


	@Test
	void givenCustomerDto_WhencreateCustomer_ThenReturnStatusCreated() throws JsonProcessingException, Exception {
		CustomerDto customerDto = CustomerDto.builder()
				.customerId("bc84d4bf-37f1-403f-b276-4b16827feb2f")
				.dateCreated("12/2/2022")
				.name("Caren")
				.surName("Donalds").build();

		mockMvc.perform(post("/v1/customers").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(customerDto)))
				.andExpect(status().isCreated())
				.andDo(print());
		
		verify(customerService).createNewCustomer(customerDto);
	}
	
	@Test
	void givenCustomerDto_WhenGetCustomerById_ThenReturnCustomer() throws Exception {
		CustomerDto customerDto = CustomerDto.builder()
				.customerId("bc84d4bf-37f1-403f-b276-4b16827feb2f")
				.dateCreated("12/2/2022")
				.name("Caren")
				.surName("Donalds").build();
		String id = "bc84d4bf-37f1-403f-b276-4b16827feb2f";
		
		when(customerService.findCustomerById(id)).thenReturn(Optional.of(customerDto));
		
		mockMvc.perform(get("/v1/customers/{id}",id))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.customerId").value(id))
		.andExpect(jsonPath("$.name").value(customerDto.getName()))
		.andExpect(jsonPath("$.surName").value(customerDto.getSurName()))
		.andExpect(jsonPath("$.dateCreated").value(customerDto.getDateCreated()))
		.andDo(print());
	}
	
	@Test
	void givenInvalidCustomerId_WhenGetCustomerById_ThenReturnCustomerNotFound() throws Exception {
		String id = "bc84d4bf-37f1-403f-b276-4b16827feb2f";
		
		when(customerService.findCustomerById(id)).thenReturn(Optional.empty());
		
		mockMvc.perform(get("/v1/customers/{id}",id))
		.andExpect(status().isNotFound())
		.andDo(print());
	}
	
	@Test
	void givenCustomerId_WhenDeleteCustomerById_ThenReturnNoFound() throws Exception {
		String id = "bc84d4bf-37f1-403f-b276-4b16827feb2f";

		doNothing().when(customerService).deleteCustomer(id);
		
		mockMvc.perform(delete("/v1/customers/{id}",id))
			   .andExpect(status().isNotFound())
			   .andDo(print());
	}
	
	@Test
	void givenCustomerDto_WhenUpdate_ThenReturnUpdatedCustomer() throws Exception {
		CustomerDto customerDto = CustomerDto.builder()
				.customerId("bc84d4bf-37f1-403f-b276-4b16827feb2f")
				.dateCreated("12/2/2022")
				.name("Caren")
				.surName("Donalds").build();
		
		CustomerDto UpdatedCustomerDto = CustomerDto.builder()
				.customerId("bc84d4bf-37f1-403f-b276-4b16827feb2f")
				.dateCreated("12/2/2022")
				.name("updated")
				.surName("updated").build();

		String id = "bc84d4bf-37f1-403f-b276-4b16827feb2f";
		
		when(customerService.findCustomerById(id)).thenReturn(Optional.of(customerDto));
		when(customerService.updateCustomer(any(CustomerDto.class))).thenReturn(UpdatedCustomerDto);

		mockMvc.perform(put("/v1/customers/{id}",id).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(UpdatedCustomerDto)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.customerId").value(id))
		.andExpect(jsonPath("$.name").value(UpdatedCustomerDto.getName()))
		.andExpect(jsonPath("$.surName").value(UpdatedCustomerDto.getSurName()))
		.andExpect(jsonPath("$.dateCreated").value(UpdatedCustomerDto.getDateCreated()))
		.andDo(print());
	}

	@Test
	void givenCustomerDto_WhenUpdate_ThenReturnC() throws Exception {
		CustomerDto UpdatedCustomerDto = CustomerDto.builder()
				.customerId("bc84d4bf-37f1-403f-b276-4b16827feb2f")
				.dateCreated("12/2/2022")
				.name("updated")
				.surName("updated").build();

		String id = "bc84d4bf-37f1-403f-b276-4b16827feb2f";

		when(customerService.findCustomerById(id)).thenReturn(Optional.empty());
		when(customerService.updateCustomer(any(CustomerDto.class))).thenReturn(UpdatedCustomerDto);

		mockMvc.perform(put("/v1/customers/{id}",id).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(UpdatedCustomerDto)))
		.andExpect(status().isNotFound())
		.andDo(print());
	}

}
