package com.crewmeister.cmcodingchallenge.reader.impl;

import com.google.common.flogger.FluentLogger;

import com.crewmeister.cmcodingchallenge.domain.ConversionRate;
import com.crewmeister.cmcodingchallenge.reader.CSVReader;
import com.crewmeister.cmcodingchallenge.repository.ConversionRateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoricalConversionRateCSVReader implements CSVReader<ConversionRate> {

  private final ConversionRateRepository repository;

  @Autowired
  public HistoricalConversionRateCSVReader(ConversionRateRepository repository) {
    this.repository = repository;
  }

  @Value("${eufx.filename}")
  private String FILE_NAME ;
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  @Override
  public void processCSV() {
    try {
      List<String[]> recordsToInsert = readCSV();
      List<ConversionRate> conversionRatesList = recordsToInsert.stream()
              .map(this::getConversionRate)
              .flatMap(List::stream)
              .collect(Collectors.toList());

      this.insertRecords(conversionRatesList);
    } catch (FileNotFoundException ex) {
      logger.atInfo().withCause(ex).log("Message: %s", ex.getMessage());
    }

  }


  private List<ConversionRate> getConversionRate(String[] conversionRates) {

    List<ConversionRate> conversionRateList = new ArrayList<>();
    String[] recordForConvRate = new String[3];
    recordForConvRate[0] = conversionRates[0];
    int counter = 0;
    for (String conversionRate : conversionRates) {

      if (conversionRate.equals("N/A") || counter == 0 || conversionRate.isEmpty()) {
        counter++;
        continue;
      }

      recordForConvRate[1] = String.valueOf(counter++);
      recordForConvRate[2] = conversionRate;

      ConversionRate rate = this.mapRecordToDomain(recordForConvRate);
      conversionRateList.add(rate);
    }
    return conversionRateList;
  }

  @Override
  public List<String[]> readCSV() throws FileNotFoundException {
    File file = ResourceUtils.getFile("classpath:" + FILE_NAME);

    List<String[]> conversionListByDate = new ArrayList<>();

    try (InputStream fileInputStream = new FileInputStream(file);
         Reader fileReader = new InputStreamReader(fileInputStream)) {

      try (com.opencsv.CSVReader csvReader = new com.opencsv.CSVReader(fileReader)) {
        String[] values;
        boolean initial = true;
        while ((values = csvReader.readNext()) != null) {
          if (initial) {
            initial = false;
            continue;
          }
          conversionListByDate.add(values);
        }
      }
    } catch (IOException ex) {
      logger.atInfo().withCause(ex).log("Message: %s", ex.getMessage());
    }

    return conversionListByDate;
  }

  @Override
  public ConversionRate mapRecordToDomain(String[] conversionRates) {
    ConversionRate conversionRate = new ConversionRate();
    LocalDate date = null;
    try {
      date = LocalDate.parse(conversionRates[0]);
    } catch (Exception ex) {
      logger.atSevere().withCause(ex).log("Message: %s", ex.getMessage());
    }
    conversionRate.setCurrencyId(Long.valueOf(conversionRates[1]));
    conversionRate.setActiveDate(date);
    conversionRate.setConversionRate(new BigDecimal(conversionRates[2]));
    return conversionRate;
  }

  @Override
  public void insertRecords(final List<ConversionRate> records) {

    this.repository.saveAll(records);
  }

}
