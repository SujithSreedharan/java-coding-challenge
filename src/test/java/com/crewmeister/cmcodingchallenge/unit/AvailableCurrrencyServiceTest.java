package com.crewmeister.cmcodingchallenge.unit;

import com.crewmeister.cmcodingchallenge.dto.CurrencyDTO;
import com.crewmeister.cmcodingchallenge.repository.CurrencyRepository;
import com.crewmeister.cmcodingchallenge.service.AvailableCurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AvailableCurrrencyServiceTest {
    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks // auto inject helloRepository
    private AvailableCurrencyService currencyService;

    @BeforeEach
    void setMockOutput() {
        when(currencyRepository.findAll()).thenReturn(new ArrayList<>());
    }

    @DisplayName("Test get avail currencies method by mocking our the repository")
    @Test
    void testGet() {
        CurrencyDTO currencyDTOS = currencyService.getAvailCurrencies();
        assertEquals(0, currencyDTOS.getCurrencies().size());
    }
}
