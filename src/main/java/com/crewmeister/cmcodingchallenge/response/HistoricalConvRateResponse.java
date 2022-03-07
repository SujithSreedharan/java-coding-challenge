package com.crewmeister.cmcodingchallenge.response;

import com.crewmeister.cmcodingchallenge.dto.ConversionRateDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class HistoricalConvRateResponse {

  public HistoricalConvRateResponse(){

  }

  public HistoricalConvRateResponse(Map<LocalDate, List<ConversionRateDTO>> data){
   this.data=data;
  }
  private Map<LocalDate, List<ConversionRateDTO>> data;

  public Map<LocalDate, List<ConversionRateDTO>> getData() {
    return data;
  }

  public void setData(final Map<LocalDate, List<ConversionRateDTO>> data) {
    this.data = data;
  }
}
