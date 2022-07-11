package com.rsaad.inttest.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsaad.controller.AccountController;
import com.rsaad.dto.AccountDto;
import com.rsaad.dto.OpenAccountDto;
import com.rsaad.service.AccountService;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {
	@MockBean
	private AccountService accountService;
	@Autowired
	private MockMvc mockMvc;	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	@DisplayName("Open account controller")
	void givenCustomerDto_WhenOpenAccount_ThenReturnCustomerDtoAndStatusOk() throws JsonProcessingException, Exception {
		AccountDto accountDto = AccountDto.builder()
				.accountId("XS34-5423-9880-4333")
				.balanace(2.0)
				.customerId("342D-D43S-8390-487DH")
				.dateCreated("12/6/2022").build();		
		OpenAccountDto openAccountDto = OpenAccountDto.builder().customerId("342D-D43S-8390-487DH").balance(2.0).build();
		when(accountService.openAccount(openAccountDto)).thenReturn(accountDto);
		mockMvc.perform(post("/v1/accounts/").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(openAccountDto)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.accountId").value(accountDto.getAccountId()))
		.andExpect(jsonPath("balanace").value(accountDto.getBalanace()))
		.andExpect(jsonPath("$.customerId").value(accountDto.getCustomerId()))
		.andExpect(jsonPath("$.dateCreated").value(accountDto.getDateCreated()))
		.andDo(print());
		verify(accountService).openAccount(openAccountDto);
	}

}
