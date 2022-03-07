package com.crewmeister.cmcodingchallenge.response;

import com.crewmeister.cmcodingchallenge.dto.CurrencyDTO;

public class AvailableCurrencyResponse {

  private CurrencyDTO currencies;

  public AvailableCurrencyResponse() {
  }
  public AvailableCurrencyResponse(CurrencyDTO currencyDTO) {
    this.currencies = currencyDTO;
  }

  public CurrencyDTO getCurrencies() {
    return currencies;
  }

  public void setCurrencies(final CurrencyDTO currencies) {
    this.currencies = currencies;
  }
}
