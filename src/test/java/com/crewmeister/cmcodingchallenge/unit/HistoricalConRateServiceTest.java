package com.crewmeister.cmcodingchallenge.unit;

import com.crewmeister.cmcodingchallenge.domain.ConversionRate;
import com.crewmeister.cmcodingchallenge.domain.Currency;
import com.crewmeister.cmcodingchallenge.exception.ParameterNotValidException;
import com.crewmeister.cmcodingchallenge.repository.ConversionRateRepository;
import com.crewmeister.cmcodingchallenge.response.HistoricalConvRateResponse;
import com.crewmeister.cmcodingchallenge.service.HistoricalConRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class HistoricalConRateServiceTest {
    @Mock
    private ConversionRateRepository conversionRateRepository;

    @InjectMocks // auto inject helloRepository
    private HistoricalConRateService conRateService;


    @BeforeEach
    void setMockOutput() {
        List<ConversionRate> conversionRateList = new ArrayList<>();
        ConversionRate conversionRate = new ConversionRate();
        conversionRate.setConversionRate(BigDecimal.valueOf(100l));
        conversionRate.setActiveDate(LocalDate.now());
        conversionRate.setCurrencyId(2l);
        conversionRate.setId(1);
        Currency currency = new Currency();
        currency.setId(1l);
        currency.setCurrencyCode("INR");
        currency.setCurrencyDescription("Indian Rupees");
        conversionRate.setCurrency(currency);
        conversionRateList.add(conversionRate);
        when(conversionRateRepository.findAll()).thenReturn(conversionRateList);

        final LocalDate queryDate = LocalDate.parse("2022-03-01");
        when(this.conversionRateRepository.getConversionRateByActiveDate(queryDate)).thenReturn(conversionRateList);

    }

    @DisplayName("Test getAllHistoricalData api")
    @Test
    void getAllHistoricalDataTest() {
        HistoricalConvRateResponse convRateResponse = conRateService.getAllHistoricalData();
        assertEquals(1, convRateResponse.getData().size());
    }

    @DisplayName("Test getAllHistoricalData api")
    @Test
    void getConversionRecordForDate() {

        HistoricalConvRateResponse convRateResponse = conRateService.getConversionRecordForDate("2022-03-01");
        assertEquals(1, convRateResponse.getData().size());
    }


    @DisplayName("Test getAllHistoricalData api with invalidDate")
    @Test
    void getConversionRecordForInvalidDate() {
        assertThrows(ParameterNotValidException.class,
                () -> conRateService.getConversionRecordForDate("2022-Mar-01"));
    }
}
