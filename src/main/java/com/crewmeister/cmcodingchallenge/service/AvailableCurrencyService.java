package com.crewmeister.cmcodingchallenge.service;

import com.crewmeister.cmcodingchallenge.domain.Currency;
import com.crewmeister.cmcodingchallenge.dto.CurrencyDTO;
import com.crewmeister.cmcodingchallenge.repository.CurrencyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AvailableCurrencyService {


  private final CurrencyRepository currencyRepository;

  @Autowired
  public AvailableCurrencyService(CurrencyRepository currencyRepository){
    this.currencyRepository = currencyRepository;
  }

  public CurrencyDTO getAvailCurrencies(){
    List<Currency> availCurrencyList = this.currencyRepository.findAll();

    Map<String,String> currencyMap =  availCurrencyList.stream()
            .collect(Collectors.toMap(
                    Currency::getCurrencyCode,
                    Currency::getCurrencyDescription));

    return new CurrencyDTO(currencyMap);
  }

}
