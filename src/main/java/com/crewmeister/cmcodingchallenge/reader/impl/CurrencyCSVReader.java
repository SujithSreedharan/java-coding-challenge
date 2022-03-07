package com.crewmeister.cmcodingchallenge.reader.impl;

import com.google.common.flogger.FluentLogger;

import com.crewmeister.cmcodingchallenge.domain.Currency;
import com.crewmeister.cmcodingchallenge.reader.CSVReader;
import com.crewmeister.cmcodingchallenge.repository.CurrencyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyCSVReader implements CSVReader<Currency> {

  private final CurrencyRepository currencyRepository;

  @Value("${currencies.filename}")
  private String FILE_NAME;

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  @Autowired
  public CurrencyCSVReader(CurrencyRepository currencyRepository) {
    this.currencyRepository = currencyRepository;
  }

  /**
   * Orchestrator for the CSV reader
   */
  @Override
  public void processCSV() {

    try {
      List<String[]> recordsToInsert = readCSV();
      List<Currency> currencyList = recordsToInsert.stream()
              .map(this::mapRecordToDomain)
              .collect(Collectors.toList());

      this.insertRecords(currencyList);

    } catch (FileNotFoundException ex) {
      logger.atInfo().withCause(ex).log("Message: %s", ex.getMessage());
    }
  }

  /**
   * Used to read the records from the CSV file
   *
   * @return list of records as Sting []
   */
  @Override
  public List<String[]> readCSV() throws FileNotFoundException {
    File file = ResourceUtils.getFile("classpath:" + FILE_NAME);

    List<String[]> currencyList = new ArrayList<>();

    try (InputStream fileInputStream = new FileInputStream(file);
         Reader fileReader = new InputStreamReader(fileInputStream)) {

      try (com.opencsv.CSVReader csvReader = new com.opencsv.CSVReader(fileReader)) {
        String[] values;
        while ((values = csvReader.readNext()) != null) {
          currencyList.add(values);
        }
      }

    } catch (IOException e) {
      logger.atInfo().withCause(e).log("Message: %s", e.getMessage());
    }

    return currencyList;
  }

  /**
   * Mapper function returning the currency object for the row
   *
   * @return current record
   */
  @Override
  public Currency mapRecordToDomain(String[] currencyArr) {
    Currency currency = new Currency();
    currency.setCurrencyCode(currencyArr[0]);
    currency.setCurrencyDescription(currencyArr[1]);
    return currency;
  }

  /**
   * Saves the passed in currency records to the DB
   */
  @Override
  public void insertRecords(List<Currency> currencyList) {
    logger.atInfo().log("No.of currencies to save %s", currencyList.size());
    this.currencyRepository.saveAll(currencyList);
  }
}
