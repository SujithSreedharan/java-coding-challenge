package com.crewmeister.cmcodingchallenge.exception;

/**
 * Thrown when the conversion rate record is not available for a particular currency for the queried date
 */
public class ConversionRateNotFoundException extends RuntimeException {

  public ConversionRateNotFoundException(final String message) {
    super(message);
  }
}
