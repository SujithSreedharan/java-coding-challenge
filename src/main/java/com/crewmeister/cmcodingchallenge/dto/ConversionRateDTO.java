package com.crewmeister.cmcodingchallenge.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ConversionRateDTO {

  public ConversionRateDTO() {

  }

  private String currencyCode;
  private BigDecimal conversionRate;
  @JsonIgnore
  private LocalDate activeDate;

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(final String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public BigDecimal getConversionRate() {
    return conversionRate;
  }

  public void setConversionRate(final BigDecimal conversionRate) {
    this.conversionRate = conversionRate;
  }

  public LocalDate getActiveDate() {
    return activeDate;
  }

  public void setActiveDate(final LocalDate activeDate) {
    this.activeDate = activeDate;
  }
}
