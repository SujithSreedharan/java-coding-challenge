package com.crewmeister.cmcodingchallenge.dto;

import java.util.Map;

public class CurrencyDTO {
  private Map<String,String> currencies;

  public CurrencyDTO(){

  }
  public CurrencyDTO(final Map<String, String> currencies) {
    this.currencies = currencies;
  }

  public Map<String, String> getCurrencies() {
    return currencies;
  }

  public void setCurrencies(final Map<String, String> currencies) {
    this.currencies = currencies;
  }
}
