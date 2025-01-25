package com.assignment.abcfitness.ExcetionHandling;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.assignment.abcfitness.dto.ErrorResponse;

@RestControllerAdvice
public class ExceptionTranslator {

	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ErrorResponse> businessExceptionHandler(ApiException apiException) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ErrorResponse.builder().message(apiException.getMessage()).build());
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiException> handleMethodNotSupportEx(HttpRequestMethodNotSupportedException ex) {

		ApiException errorResponse = new ApiException("Method Not Supported ", ex.getStatusCode());

		return new ResponseEntity<>(errorResponse, ex.getStatusCode());

	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiException> handleBadRequestEx(BadRequestException ex) {

		ApiException errorResponse = new ApiException("Method Not Supported ", HttpStatus.BAD_REQUEST);

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiException> handleException(Exception ex) {
		ApiException errorResponse = new ApiException(
				"Unable to Process Request.",
				HttpStatus.INTERNAL_SERVER_ERROR);

		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
