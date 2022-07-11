package com.rsaad.inttest.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsaad.controller.TransactionController;
import com.rsaad.service.TransactionService;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {
	
	@MockBean
	private TransactionService tracnsactionService;

	@Autowired
	private MockMvc mockMvc;	
	@Autowired
	private ObjectMapper objectMapper;
	
//	@Test
//	@DisplayName("case customer has transactions")
//	void givenCustomerDto_WhenCallCustomerInfo_ThenReturnCustomerInfo() throws JsonProcessingException, Exception {

}
