package com.crewmeister.cmcodingchallenge.integration;

import com.crewmeister.cmcodingchallenge.constant.CMConstants;
import com.crewmeister.cmcodingchallenge.request.CurrencyConversionQueryReq;
import com.crewmeister.cmcodingchallenge.response.ConversionRateResponse;
import com.crewmeister.cmcodingchallenge.response.ExceptionResponse;
import com.crewmeister.cmcodingchallenge.response.HistoricalConvRateResponse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConversionRateControllerTest {

  private static final String CONV_RATE_END_POINT = "/api/cm/v1/conversion-rate";
  private static final String HIST_CONV_RATE_END_POINT = "/api/cm/v1/historical-conversion-rate";
  private static final String VALID_QUERY_DT = "2022-03-02";
  private static final String INVALID_QUERY_DT = "2022-MAR-2022";
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @DisplayName("Test the conversion rates returned by for given Date endpoint")
  @Test
  void getConversionRateForValidDate() throws MalformedURLException {
    ResponseEntity<HistoricalConvRateResponse> response = restTemplate.getForEntity(
            new URL("http://localhost:" + port + CONV_RATE_END_POINT + "?date=" + VALID_QUERY_DT).toString(),
            HistoricalConvRateResponse.class);
    assertTrue(response!=null);
    assertEquals(response.getStatusCode(), HttpStatus.OK);
    HistoricalConvRateResponse convRateResponse= response.getBody();
    assertEquals(convRateResponse.getData().size(), 1);
    assertTrue(convRateResponse.getData().get(LocalDate.parse(VALID_QUERY_DT)).size() > 0);
  }

  @DisplayName("Test the conversion rates response for invalid Date")
  @Test
  void getConversionRateForInValidDate() throws MalformedURLException {
    ResponseEntity<HistoricalConvRateResponse> response = restTemplate.getForEntity(
            new URL("http://localhost:" + port + CONV_RATE_END_POINT + "?date=" + INVALID_QUERY_DT).toString(),
            HistoricalConvRateResponse.class);
    assertTrue(response!=null);
    assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
  }

  @DisplayName("Test the historical conversion rate response")
  @Test
  void getHistoricalConversionRates() throws MalformedURLException {
    ResponseEntity<HistoricalConvRateResponse> response = restTemplate.getForEntity(
            new URL("http://localhost:" + port + HIST_CONV_RATE_END_POINT).toString(),
            HistoricalConvRateResponse.class);
    assertTrue(response!=null);
    assertEquals(response.getStatusCode(), HttpStatus.OK);
    HistoricalConvRateResponse convRateResponse= response.getBody();
    //No.of rows (total dates) in the excel used for data load excluding header row
    assertEquals(convRateResponse.getData().size(), 3);
  }

  @DisplayName("Test conversion rate to EUR for a target currency")
  @Test
  void calculateConversionRate() throws MalformedURLException {
    CurrencyConversionQueryReq conversionQueryReq = new CurrencyConversionQueryReq();
    conversionQueryReq.setConversionDate(VALID_QUERY_DT);
    conversionQueryReq.setAmount(new BigDecimal("100.00"));
    conversionQueryReq.setSourceCurrencyCode("INR");
    ResponseEntity<ConversionRateResponse> response = restTemplate.postForEntity(
            new URL("http://localhost:" + port + CONV_RATE_END_POINT).toString(),conversionQueryReq,
            ConversionRateResponse.class);
    assertTrue(response!=null);
    assertEquals(response.getStatusCode(), HttpStatus.OK);
    ConversionRateResponse convRateResponse= response.getBody();
    //No.of rows (total dates) in the excel used for data load excluding header row
    assertNotNull(convRateResponse);
    assertEquals(convRateResponse.getSourceCurrency(), "INR");
    assertTrue(convRateResponse.getSourceAmount().compareTo(new BigDecimal("100.00")) == 0);
    assertEquals(convRateResponse.getTargetCurrency(), "EUR");
    assertTrue(convRateResponse.getConversionDate().compareTo(LocalDate.parse(VALID_QUERY_DT)) ==0);
  }

  @DisplayName("Test conversion rate api with invalid dates")
  @Test
  void calculateConversionRateWithInvDt() throws MalformedURLException {
    CurrencyConversionQueryReq conversionQueryReq = new CurrencyConversionQueryReq();
    conversionQueryReq.setConversionDate(INVALID_QUERY_DT);
    conversionQueryReq.setAmount(new BigDecimal("100.00"));
    conversionQueryReq.setSourceCurrencyCode("INR");
    ResponseEntity<ExceptionResponse> response = restTemplate.postForEntity(
            new URL("http://localhost:" + port + CONV_RATE_END_POINT).toString(), conversionQueryReq,
            ExceptionResponse.class);
    assertTrue(response != null);
    assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

    ExceptionResponse exceptionResponse = response.getBody();
    assertNotNull(exceptionResponse);
    assertEquals(exceptionResponse.getMessage(),CMConstants.INVALID_DT_FORMAT_MSG);
  }

  @DisplayName("Test conversion rate api with invalid currency")
  @Test
  void calculateConversionRateWithInvCurr() throws MalformedURLException {
    CurrencyConversionQueryReq conversionQueryReq = new CurrencyConversionQueryReq();
    conversionQueryReq.setConversionDate(VALID_QUERY_DT);
    conversionQueryReq.setAmount(new BigDecimal("100.00"));
    conversionQueryReq.setSourceCurrencyCode("INRR");
    ResponseEntity<ExceptionResponse> response = restTemplate.postForEntity(
            new URL("http://localhost:" + port + CONV_RATE_END_POINT).toString(), conversionQueryReq,
            ExceptionResponse.class);
    assertTrue(response != null);
    assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

    ExceptionResponse exceptionResponse = response.getBody();
    assertNotNull(exceptionResponse);
    assertEquals(exceptionResponse.getMessage(),CMConstants.CURRENCY_NOT_FOUND_MSG);
  }

  @DisplayName("Test conversion rate api with non available currency & date combination")
  @Test
  void calculateConversionRateWithNoConvRate() throws MalformedURLException {
    CurrencyConversionQueryReq conversionQueryReq = new CurrencyConversionQueryReq();
    conversionQueryReq.setConversionDate(VALID_QUERY_DT);
    conversionQueryReq.setAmount(new BigDecimal("100.00"));
    conversionQueryReq.setSourceCurrencyCode("CYP");
    ResponseEntity<ExceptionResponse> response = restTemplate.postForEntity(
            new URL("http://localhost:" + port + CONV_RATE_END_POINT).toString(), conversionQueryReq,
            ExceptionResponse.class);
    assertTrue(response != null);
    assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

    ExceptionResponse exceptionResponse = response.getBody();
    assertNotNull(exceptionResponse);
    assertEquals(exceptionResponse.getMessage(), CMConstants.CONVERSION_RATE_NOT_FOUND_MSG);
  }
}
