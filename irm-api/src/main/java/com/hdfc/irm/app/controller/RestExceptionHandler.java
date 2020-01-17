package com.hdfc.irm.app.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hdfc.irm.engine.exception.ApiError;
import com.hdfc.irm.engine.exception.InvalidRequestException;
import com.hdfc.irm.engine.exception.PayoutLimitNotSetException;
import com.hdfc.irm.engine.utils.LoggerUtils;

@ControllerAdvice(basePackages = "com.hdfc.irm")
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private static Logger logger = Logger.getLogger(RestExceptionHandler.class);

	@ExceptionHandler(PayoutLimitNotSetException.class)
	protected ResponseEntity<Object> handleConnectException(PayoutLimitNotSetException ex, WebRequest request) {
		return buildResponseEntity(new ApiError(HttpStatus.PRECONDITION_REQUIRED, ex.getMessage()));
	}

	@ExceptionHandler(InvalidRequestException.class)
	protected ResponseEntity<Object> handleConnectException(InvalidRequestException ex, WebRequest request) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleConnectException(Exception ex, WebRequest request) {
		logger.error(LoggerUtils.getStackStrace(ex));
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<Object>(apiError, apiError.getStatus());
	}

}
