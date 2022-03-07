package com.crewmeister.cmcodingchallenge.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ConversionRateResponse {

  private String sourceCurrency;
  private String targetCurrency;
  private LocalDate conversionDate;
  private BigDecimal targetAmount;
  private BigDecimal sourceAmount;

  public String getSourceCurrency() {
    return sourceCurrency;
  }

  public void setSourceCurrency(final String sourceCurrency) {
    this.sourceCurrency = sourceCurrency;
  }

  public String getTargetCurrency() {
    return targetCurrency;
  }

  public void setTargetCurrency(final String targetCurrency) {
    this.targetCurrency = targetCurrency;
  }

  public LocalDate getConversionDate() {
    return conversionDate;
  }

  public void setConversionDate(final LocalDate conversionDate) {
    this.conversionDate = conversionDate;
  }

  public BigDecimal getTargetAmount() {
    return targetAmount;
  }

  public void setTargetAmount(final BigDecimal targetAmount) {
    this.targetAmount = targetAmount;
  }

  public BigDecimal getSourceAmount() {
    return sourceAmount;
  }

  public void setSourceAmount(final BigDecimal sourceAmount) {
    this.sourceAmount = sourceAmount;
  }
}
