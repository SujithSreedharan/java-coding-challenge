package com.crewmeister.cmcodingchallenge.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.*;

@Entity(name = "CONVERSION_RATE")
public class ConversionRate {

  public ConversionRate() {

  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @JoinColumn(name = "CURRENCY_ID", insertable = false, updatable = false)
  @ManyToOne(targetEntity = Currency.class, fetch = FetchType.EAGER)
  private Currency currency;

  @Column(name = "CURRENCY_ID")
  private Long currencyId;

  @Column(name = "ACTIVE_DATE")
  private LocalDate activeDate;

  @Column(name = "CON_RATE", precision = 32, scale = 4)
  private BigDecimal conversionRate;

  public long getId() {
    return id;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(final Currency currency) {
    this.currency = currency;
  }

  public LocalDate getActiveDate() {
    return activeDate;
  }

  public void setActiveDate(final LocalDate addedDate) {
    this.activeDate = addedDate;
  }

  public Long getCurrencyId() {
    return currencyId;
  }

  public void setCurrencyId(final Long currencyId) {
    this.currencyId = currencyId;
  }

  public BigDecimal getConversionRate() {
    return conversionRate;
  }

  public void setConversionRate(final BigDecimal conversionRate) {
    this.conversionRate = conversionRate;
  }
}
