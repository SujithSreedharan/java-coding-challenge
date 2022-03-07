package com.crewmeister.cmcodingchallenge.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

/**
 * Post Payload for currency conversion Query
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyConversionQueryReq {

  private String sourceCurrencyCode;
  private String targetCurrencyCode = "EUR";
  private String conversionDate;
  private BigDecimal amount;

  public String getSourceCurrencyCode() {
    return sourceCurrencyCode;
  }

  public void setSourceCurrencyCode(final String sourceCurrencyCode) {
    this.sourceCurrencyCode = sourceCurrencyCode;
  }

  public String getTargetCurrencyCode() {
    return targetCurrencyCode;
  }

  public void setTargetCurrencyCode(final String targetCurrencyCode) {
    this.targetCurrencyCode = targetCurrencyCode;
  }

  public String getConversionDate() {
    return conversionDate;
  }

  public void setConversionDate(final String conversionDate) {
    this.conversionDate = conversionDate;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(final BigDecimal amount) {
    this.amount = amount;
  }
}
