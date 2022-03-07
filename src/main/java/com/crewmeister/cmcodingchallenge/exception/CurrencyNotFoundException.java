package com.crewmeister.cmcodingchallenge.exception;

/**
 * Thrown when the passed in currency is not found in the db
 */
public class CurrencyNotFoundException extends RuntimeException{

  public CurrencyNotFoundException(final String message){
    super(message);
  }

}
