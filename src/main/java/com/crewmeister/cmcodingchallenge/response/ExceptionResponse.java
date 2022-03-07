package com.crewmeister.cmcodingchallenge.response;

import java.time.LocalDateTime;

public class ExceptionResponse {

  private String message;
  private LocalDateTime dateTime;

  public ExceptionResponse(final String message, final LocalDateTime time) {
    this.message = message;
    this.dateTime = time;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }
}