package com.prudential.phi.operation.exception;

import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice("com.prudential.phi.operation.controller")
@Slf4j
public class CustomExceptionHandler {

	
	@ExceptionHandler(value = { Exception.class })
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage resourceNotFoundException(Exception ex, WebRequest request) {
		log.error("Exception is :"+ex);
		log.error(ex.getMessage(),ex);
		ErrorMessage message = new ErrorMessage(ex.getMessage());
		return message;
	}
	
}