package com.crewmeister.cmcodingchallenge.domain;

import javax.persistence.*;

@Entity
public class Currency {


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private  long id;
  @Column(name = "CODE")
  private String currencyCode;
  @Column(name = "DESCRIPTION")
  private String currencyDescription;

  public Currency() {
  }

  public long getId() {
    return id;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(final String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public String getCurrencyDescription() {
    return currencyDescription;
  }

  public void setCurrencyDescription(final String currencyDescription) {
    this.currencyDescription = currencyDescription;
  }
}
