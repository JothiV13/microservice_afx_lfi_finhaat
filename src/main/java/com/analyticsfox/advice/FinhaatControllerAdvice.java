package com.analyticsfox.advice;

import com.analyticsfox.dto.ApiError;
import org.hibernate.TypeMismatchException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.ClassUtils;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.persistence.EntityNotFoundException;
import java.net.BindException;

@RestControllerAdvice
public class FinhaatControllerAdvice {

    private static final String BAD_REQUEST = "400 Bad Request";

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ApiError> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        ApiError apiError = new ApiError();
        apiError.setStatus(405);
        apiError.setError("405 Method Not Allowed");
        apiError.setMessage(String.format("Request method %s not supported", exception.getMethod()));
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(apiError);
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<ApiError> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception) {
        ApiError apiError = new ApiError();
        apiError.setStatus(415);
        apiError.setError("415 UnSupported Media Type");
        apiError.setMessage(String.format("Content type %s not supported", exception.getContentType()));
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(apiError);
    }

    @ExceptionHandler({MissingPathVariableException.class})
    public ResponseEntity<ApiError> handleMissingPathVariableException(MissingPathVariableException exception) {
        ApiError apiError = new ApiError();
        apiError.setStatus(400);
        apiError.setError(BAD_REQUEST);
        apiError.setMessage(String.format("Missing URI template variable %s", exception.getVariableName()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<ApiError> handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        ApiError apiError = new ApiError();
        apiError.setStatus(400);
        apiError.setError(BAD_REQUEST);
        apiError.setMessage(String.format("Required parameter %s is not present", exception.getParameterName()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler({TypeMismatchException.class})
    public ResponseEntity<ApiError> handleTypeMismatchException(TypeMismatchException exception) {
        ApiError apiError = new ApiError();
        apiError.setStatus(400);
        apiError.setError(BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<ApiError> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException  exception) {
        ApiError apiError = new ApiError();
        apiError.setStatus(400);
        apiError.setError(BAD_REQUEST);
        apiError.setMessage(String.format("%s should be of type %s", exception.getName(), ClassUtils.getQualifiedName(exception.getRequiredType())));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ApiError> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        ApiError apiError = new ApiError();
        apiError.setStatus(400);
        apiError.setError(BAD_REQUEST);
        apiError.setMessage("Malformed JSON request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ApiError apiError = new ApiError();
        apiError.setStatus(400);
        apiError.setMessage("Validation errors");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler({BindException.class})
    public ResponseEntity<ApiError> handleBindException(BindException exception) {
        ApiError apiError = new ApiError();
        apiError.setStatus(400);
        apiError.setMessage("Validation errors");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler({ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        ApiError apiError = new ApiError();
        apiError.setStatus(400);
        apiError.setMessage("Validation errors");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler({MissingServletRequestPartException.class})
    public ResponseEntity<ApiError> handleMissingServletRequestPartException(MissingServletRequestPartException exception) {
        ApiError apiError = new ApiError();
        apiError.setStatus(400);
        apiError.setError(BAD_REQUEST);
        apiError.setMessage(String.format("Required request part %s is not present", exception.getRequestPartName()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<ApiError> handleNoHandlerFoundException(NoHandlerFoundException exception) {
        ApiError apiError = new ApiError();
        apiError.setStatus(404);
        apiError.setError("404 Not Found");
        apiError.setMessage(String.format("No handler found for %s %s", exception.getHttpMethod(), exception.getRequestURL()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler({AsyncRequestTimeoutException.class})
    public ResponseEntity<ApiError> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException exception, WebRequest request) {
        ApiError apiError = new ApiError();
        apiError.setStatus(503);
        apiError.setError("503 Service Unavailable");
        apiError.setMessage("Service Not Available");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(apiError);
    }

    @ExceptionHandler({ServiceException.class})
    public ResponseEntity<ApiError> handleEventServiceException(ServiceException exception) {
        ApiError apiError = new ApiError();
        apiError.setStatus(412);
        apiError.setError("412 Precondition Failed");
        apiError.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(apiError);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiError> handleException(Exception exception) {
        ApiError apiError = new ApiError();
        apiError.setStatus(500);
        apiError.setError("Internal Server Error");
        apiError.setMessage("Something went wrong !!");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException exception) {
        ApiError apiError = new ApiError();
        apiError.setStatus(404);
        apiError.setError("404 Not Found");
        apiError.setMessage(exception.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

}
