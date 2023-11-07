package com.analyticsfox.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public final ResponseEntity<Object> handleAllExceptions(RuntimeException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), request.getDescription(false), "",
				true, "500", null);
		System.out.println(ex);
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Forbidden.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public final ResponseEntity<Object> handleAllExceptions(Forbidden ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), request.getDescription(false), "",
				true, "403", null);
		System.out.println(ex);
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.FORBIDDEN);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());
		ExceptionResponse exceptionResponse = new ExceptionResponse("Validation Failed",
				ex.getBindingResult().toString(), "", true, "400", errors);
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
}
