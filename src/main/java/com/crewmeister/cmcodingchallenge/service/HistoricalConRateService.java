package com.crewmeister.cmcodingchallenge.service;

import com.google.common.flogger.FluentLogger;

import com.crewmeister.cmcodingchallenge.constant.CMConstants;
import com.crewmeister.cmcodingchallenge.domain.ConversionRate;
import com.crewmeister.cmcodingchallenge.domain.Currency;
import com.crewmeister.cmcodingchallenge.dto.ConversionRateDTO;
import com.crewmeister.cmcodingchallenge.exception.ConversionRateNotFoundException;
import com.crewmeister.cmcodingchallenge.exception.CurrencyNotFoundException;
import com.crewmeister.cmcodingchallenge.exception.ParameterNotValidException;
import com.crewmeister.cmcodingchallenge.repository.ConversionRateRepository;
import com.crewmeister.cmcodingchallenge.repository.CurrencyRepository;
import com.crewmeister.cmcodingchallenge.request.CurrencyConversionQueryReq;
import com.crewmeister.cmcodingchallenge.response.ConversionRateResponse;
import com.crewmeister.cmcodingchallenge.response.HistoricalConvRateResponse;
import com.crewmeister.cmcodingchallenge.util.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HistoricalConRateService {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  private final ConversionRateRepository conversionRateRepository;
  private final CurrencyRepository currencyRepository;

  @Autowired
  public HistoricalConRateService(ConversionRateRepository conversionRateRepository, CurrencyRepository currencyRepository) {
    this.conversionRateRepository = conversionRateRepository;
    this.currencyRepository = currencyRepository;
  }

  /**
   * Method for getting all the historical currency conversion data as a Map which are grouped by
   * date
   *
   * @return HistoricalConvRateResponse
   */
  public HistoricalConvRateResponse getAllHistoricalData() {
    final List<ConversionRate> conversionRateList = this.conversionRateRepository.findAll();

    final Map<LocalDate, List<ConversionRateDTO>> responseMap = conversionRateList.stream().map(this::mapToConversionRateDTO)
            .collect(Collectors.toMap(ConversionRateDTO::getActiveDate, Arrays::asList, this::mergeList));
    logger.atInfo().log("To Mapped conversions : %s", responseMap.size());
    return new HistoricalConvRateResponse(responseMap);
  }

  /**
   * Get the available conversion records from the database for the passed in date
   *
   * @return HistoricalConvRateResponse
   */
  public HistoricalConvRateResponse getConversionRecordForDate(final String activeDate) {

    final boolean isValidDate = DateUtils.isValidDate(activeDate);
    logger.atInfo().log("Passed in date %s is %s", activeDate, isValidDate);
    if (!isValidDate) throw new ParameterNotValidException(CMConstants.INVALID_DT_FORMAT_MSG);
    final LocalDate queryDate = LocalDate.parse(activeDate);
    final List<ConversionRate> conversionRateList = this.conversionRateRepository.getConversionRateByActiveDate(queryDate);

    final Map<LocalDate, List<ConversionRateDTO>> responseMap = conversionRateList.stream().map(this::mapToConversionRateDTO)
            .collect(Collectors.toMap(ConversionRateDTO::getActiveDate, Arrays::asList, this::mergeList));

    return new HistoricalConvRateResponse(responseMap);
  }


  /**
   * Mapper method for converting the conversion rate entity object to DTO
   */
  private ConversionRateDTO mapToConversionRateDTO(final ConversionRate conversionRate) {
    final ConversionRateDTO conversionRateDTO = new ConversionRateDTO();
    conversionRateDTO.setConversionRate(conversionRate.getConversionRate());
    conversionRateDTO.setCurrencyCode(conversionRate.getCurrency().getCurrencyCode());
    conversionRateDTO.setActiveDate(conversionRate.getActiveDate());
    return conversionRateDTO;
  }

  /**
   * Merge the passed in conversion DTO list in to a single list
   */
  private List<ConversionRateDTO> mergeList(final List<ConversionRateDTO> e1, final List<ConversionRateDTO> e2) {
    final List<ConversionRateDTO> mergedList = new ArrayList<>();
    mergedList.addAll(e1);
    mergedList.addAll(e2);
    return mergedList;
  }

  /**
   * Calculate the conversion rate to EUR for the passed in source currency and amount
   */
  public ConversionRateResponse calculateConversionRate(final CurrencyConversionQueryReq conversionQueryReq) {

    //Fetch the currency record from the db and throw exception if not found
    final Optional<Currency> currencyOptional = this.currencyRepository.getCurrencyByCurrencyCode(conversionQueryReq.getSourceCurrencyCode());
    if (currencyOptional.isEmpty()) {
      throw new CurrencyNotFoundException(CMConstants.CURRENCY_NOT_FOUND_MSG);
    }

    if (!DateUtils.isValidDate(conversionQueryReq.getConversionDate())) {
      throw new ParameterNotValidException(CMConstants.INVALID_DT_FORMAT_MSG);
    }
    final Currency sourceCurrency = currencyOptional.get();
    final LocalDate activeDate = LocalDate.parse(conversionQueryReq.getConversionDate());

    final Optional<ConversionRate> conversionRateOptional = this.conversionRateRepository
            .getConversionRateByActiveDateAndCurrencyId(activeDate, sourceCurrency.getId());

    if (conversionRateOptional.isEmpty()) {
      throw new ConversionRateNotFoundException(CMConstants.CONVERSION_RATE_NOT_FOUND_MSG);
    }

    final ConversionRate conversionRate = conversionRateOptional.get();
    final BigDecimal eurConvAmountForSourCurr = conversionRate.getConversionRate();

    final BigDecimal sourceCurrencyAmount = conversionQueryReq.getAmount();

    final BigDecimal equivEurAmount = sourceCurrencyAmount.divide(eurConvAmountForSourCurr, 4, RoundingMode.HALF_UP);

    logger.atInfo().log("Passed in currency %s . Source Amount %s. eurConvAmountForSourCurr =%s . Converted EUR val %s",
            sourceCurrency.getCurrencyCode(), sourceCurrencyAmount, eurConvAmountForSourCurr, equivEurAmount);

    final ConversionRateResponse conversionRateResponse = new ConversionRateResponse();
    conversionRateResponse.setConversionDate(activeDate);
    conversionRateResponse.setSourceCurrency(conversionQueryReq.getSourceCurrencyCode());
    conversionRateResponse.setTargetCurrency(conversionQueryReq.getTargetCurrencyCode());
    conversionRateResponse.setSourceAmount(sourceCurrencyAmount);
    conversionRateResponse.setTargetAmount(equivEurAmount);
    return conversionRateResponse;

  }
}
