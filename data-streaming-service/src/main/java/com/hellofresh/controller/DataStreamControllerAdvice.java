package com.hellofresh.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hellofresh.exceptions.ApplicationException;
import com.hellofresh.model.ExceptionResponse;


@RestControllerAdvice
public class DataStreamControllerAdvice {

	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<ExceptionResponse> handleOtherExceptions(ApplicationException ex) {
		ExceptionResponse response = new ExceptionResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		return ResponseEntity.ok().body(response);
	}
}
