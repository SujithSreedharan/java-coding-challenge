package com.crewmeister.cmcodingchallenge.integration;

import com.crewmeister.cmcodingchallenge.dto.CurrencyDTO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CurrencyControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  private static final String END_POINT="/api/cm/v1/available-currencies";

  @DisplayName("Test the currencies returned by available-currencies endpoint")
  @Test
  void testGetCurrencies() throws MalformedURLException {

    ResponseEntity<CurrencyDTO> response = restTemplate.getForEntity(
            new URL("http://localhost:" + port + END_POINT).toString(), CurrencyDTO.class);
    assertTrue(response!=null);
    assertEquals(response.getStatusCode(), HttpStatus.OK);
    CurrencyDTO currencyDTO = response.getBody();
    assertEquals(currencyDTO.getCurrencies().size() , 41);
  }

}
