package com.hdfc.irm.web.rest.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hdfc.irm.engine.exception.ApiError;
import com.hdfc.irm.web.exceptions.IRMAuthenticateException;

@ControllerAdvice(basePackages = "com.hdfc.irm")
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(IRMAuthenticateException.class)
	protected ResponseEntity<Object> handleConnectException(IRMAuthenticateException ex, WebRequest request) {
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<Object>(apiError, apiError.getStatus());
	}

}
