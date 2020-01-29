package com.hdfc.irm.app.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hdfc.irm.engine.exception.ApiError;
import com.hdfc.irm.engine.utils.LoggerUtils;

@ControllerAdvice(basePackages = "com.hdfc.irm")
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private static Logger logger = Logger.getLogger(RestExceptionHandler.class);

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<Object>(apiError, apiError.getStatus());
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleConnectException(Exception ex, WebRequest request) {
		logger.error(LoggerUtils.getStackStrace(ex));
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ApiError error = new ApiError(status, ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
		return buildResponseEntity(error);

	}
}
