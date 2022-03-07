package com.crewmeister.cmcodingchallenge.controller;

import com.crewmeister.cmcodingchallenge.dto.CurrencyDTO;
import com.crewmeister.cmcodingchallenge.service.AvailableCurrencyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/cm/v1/")
public class CurrencyController {


    private final AvailableCurrencyService availableCurrencyService;
    @Autowired
    public CurrencyController(final AvailableCurrencyService availableCurrencyService){
      this.availableCurrencyService = availableCurrencyService;
    }

  @GetMapping(value = "/available-currencies", produces = "application/json")
  public ResponseEntity<CurrencyDTO> getCurrencies() {
    CurrencyDTO response = this.availableCurrencyService.getAvailCurrencies();

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
