package com.crewmeister.cmcodingchallenge.aop;

import com.crewmeister.cmcodingchallenge.exception.ConversionRateNotFoundException;
import com.crewmeister.cmcodingchallenge.exception.CurrencyNotFoundException;
import com.crewmeister.cmcodingchallenge.exception.ParameterNotValidException;
import com.crewmeister.cmcodingchallenge.response.ExceptionResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * Global Handler for all the errors
 */
@ControllerAdvice
public class GlobalExceptionHandler {


  @ExceptionHandler(value = {CurrencyNotFoundException.class,ConversionRateNotFoundException.class})
  public ResponseEntity<ExceptionResponse> currencyNotFoundException(final RuntimeException ex) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(
            ex.getMessage(), LocalDateTime.now());
    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = ParameterNotValidException.class)
  public ResponseEntity<ExceptionResponse> parameterNotValidException(final ParameterNotValidException ex) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(
            ex.getMessage(), LocalDateTime.now());
    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = RuntimeException.class)
  public ResponseEntity<ExceptionResponse> runtimeException(final RuntimeException ex) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(
            "Error processing request. Please contact administrator.", LocalDateTime.now());
    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}