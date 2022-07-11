package com.rsaad.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rsaad.constants.CustomerConstants;

@ControllerAdvice
public class AccoundExceptionController {

	@ExceptionHandler(value = CustomerNotFoundException.class)
	public ResponseEntity<Object> exception(CustomerNotFoundException exception){
		return new ResponseEntity<Object>(CustomerConstants.CUSTOMER_DOES_NOT_EXIST, HttpStatus.NOT_FOUND);
	}
}
