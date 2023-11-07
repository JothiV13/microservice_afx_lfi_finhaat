package com.analyticsfox.exception;

import java.util.List;

public class ExceptionResponse {
	private String message;
	private String details;
	private String data;
	private boolean error;
	private String statusCode;
	List<String> errors;

	public ExceptionResponse(String message, String details, String data, boolean errorflag, String statuscode,
			List<String> errors) {
		super();
		this.message = message;
		this.details = details;
		this.data = data;
		this.error = errorflag;
		this.statusCode = statuscode;
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
