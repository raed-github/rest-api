package com.rsaad.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.rsaad.constants.AccountConstants;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends RuntimeException {
	
	public AccountNotFoundException() {
		super(AccountConstants.ACCOUNT_DOES_NOT_EXIST);
	}
	public AccountNotFoundException(String msg) {
		super(msg);
	}
}
