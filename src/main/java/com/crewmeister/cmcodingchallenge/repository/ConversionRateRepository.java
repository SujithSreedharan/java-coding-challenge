package com.crewmeister.cmcodingchallenge.repository;

import com.crewmeister.cmcodingchallenge.domain.ConversionRate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConversionRateRepository extends JpaRepository<ConversionRate, Integer> {

  List<ConversionRate> getConversionRateByActiveDate(LocalDate activeDate);
  Optional<ConversionRate> getConversionRateByActiveDateAndCurrencyId(LocalDate activeDate, long currencyId);
}
