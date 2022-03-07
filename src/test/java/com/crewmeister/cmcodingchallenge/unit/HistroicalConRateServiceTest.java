package com.crewmeister.cmcodingchallenge.unit;

import com.crewmeister.cmcodingchallenge.domain.ConversionRate;
import com.crewmeister.cmcodingchallenge.domain.Currency;
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
import static org.mockito.Mockito.when;

@SpringBootTest
public class HistroicalConRateServiceTest {
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
    }

    @DisplayName("Test getAllHistoricalData method")
    @Test
    void testGet() {
        HistoricalConvRateResponse convRateResponse = conRateService.getAllHistoricalData();
        assertEquals(1, convRateResponse.getData().size());
    }
}
