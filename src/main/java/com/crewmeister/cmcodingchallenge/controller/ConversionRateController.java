package com.crewmeister.cmcodingchallenge.controller;

import com.google.common.flogger.FluentLogger;

import com.crewmeister.cmcodingchallenge.request.CurrencyConversionQueryReq;
import com.crewmeister.cmcodingchallenge.response.ConversionRateResponse;
import com.crewmeister.cmcodingchallenge.response.HistoricalConvRateResponse;
import com.crewmeister.cmcodingchallenge.service.HistoricalConRateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/cm/v1/")
public class ConversionRateController {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  @Autowired
  public ConversionRateController(final HistoricalConRateService historicalConRateService) {
    this.historicalConRateService = historicalConRateService;
  }

  private final HistoricalConRateService historicalConRateService;

  @GetMapping(value = "/historical-conversion-rate", produces = "application/json")
  public ResponseEntity<HistoricalConvRateResponse> getHistoricalConversionRates() {
    HistoricalConvRateResponse response = this.historicalConRateService.getAllHistoricalData();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }


  @GetMapping(value = "/conversion-rate", produces = "application/json")
  public ResponseEntity<HistoricalConvRateResponse> getConvRateForDate(@RequestParam("date")final String date) {
    logger.atInfo().log("Date param value is %s", date);
    HistoricalConvRateResponse response = this.historicalConRateService.getConversionRecordForDate(date);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping(value = "/conversion-rate", produces = "application/json")
  public ResponseEntity<ConversionRateResponse> calculateConversionRate(@RequestBody final CurrencyConversionQueryReq conversionQueryReq) {
    ConversionRateResponse response = this.historicalConRateService.calculateConversionRate(conversionQueryReq);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
