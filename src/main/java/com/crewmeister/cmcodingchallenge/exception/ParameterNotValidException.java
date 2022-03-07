package com.crewmeister.cmcodingchallenge.exception;

/**
 * Custom exception thrown when the input parameter received is not valid
 */
public class ParameterNotValidException extends RuntimeException{

  public ParameterNotValidException(final String message){
    super(message);
  }
}
